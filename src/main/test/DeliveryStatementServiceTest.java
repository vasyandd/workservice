import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import work.app.WorkServiceSpringBootApplication;
import work.app.service.DeliveryStatementService;
import work.app.service.NotificationService;
import work.app.service.model.Contract;
import work.app.service.model.DeliveryStatement;
import work.app.service.model.Notification;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@SpringBootTest(classes = WorkServiceSpringBootApplication.class)
class DeliveryStatementServiceTest {

    @Autowired
    private DeliveryStatementService deliveryStatementService;

    @Autowired
    private NotificationService notificationService;

    private static Contract contract;
    private static DeliveryStatement testDeliveryStatement;
    private static final List<Notification> testNotifications = new ArrayList<>();

    @BeforeAll
    static void createTestData() {
        List<DeliveryStatement.Row> rows = new ArrayList<>();
        contract = new Contract("1234567/", LocalDate.now(), 2);
        for (int j = 0; j < 6; j++) {
            int period = ThreadLocalRandom.current().nextInt(2020, 2025);
            int month1 = ThreadLocalRandom.current().nextInt(1, 13);
            int month2 = ThreadLocalRandom.current().nextInt(1, 13);
            int month3 = ThreadLocalRandom.current().nextInt(1, 13);
            Map<Month, Integer> shipment = new HashMap<>();
            shipment.put(Month.of(month1), 37);
            shipment.put(Month.of(month2), 137);
            shipment.put(Month.of(month3), 237);
            DeliveryStatement.Row row = new DeliveryStatement.Row(new BigInteger("1231424"),
                    "Korpus" + j, shipment, period);
            rows.add(row);
            int productQuantity1 = ThreadLocalRandom.current().nextInt(1000);
            int productQuantity2 = ThreadLocalRandom.current().nextInt(1000);
            Notification notification1 = new Notification(null, LocalDate.of(period, 6, 6),
                    "Korpus" + j, productQuantity1, "", contract);
            Notification notification2 = new Notification(5, LocalDate.of(period, 1, 1),
                    "Korpus" + j, productQuantity2, "400-999", contract);
            testNotifications.add(notification1);
            testNotifications.add(notification2);
        }
        testDeliveryStatement = new DeliveryStatement(4, contract, rows);
    }


    @BeforeEach
    void clear() {
        deliveryStatementService.deleteAll();
        deliveryStatementService.saveDeliveryStatement(testDeliveryStatement);
    }

    @ParameterizedTest
    @MethodSource("returnAllNotifications")
    @DisplayName("Проверка изменения количества поставленных изделий в ведомости поставки при сохранении извещения")
    void Should_Updating_ActualShipment_In_DeliveryStatement_When_Save_Notification(Notification notification) {
        int productQuantityInNotification = notification.getProductQuantity();
        int actualShipmentInDeliveryStatementBeforeSaving = deliveryStatementService
                .getDeliveryStatementsByContract(contract)
                .getRowByProductAndPeriod(notification.getProductName(), notification.getDate().getYear())
                .getActualProductQuantity();

        Assertions.assertEquals(0 , actualShipmentInDeliveryStatementBeforeSaving);

        notificationService.saveNotification(notification);

        int actualShipmentInDeliveryStatementAfterSaving = deliveryStatementService
                .getDeliveryStatementsByContract(contract)
                .getRowByProductAndPeriod(notification.getProductName(), notification.getDate().getYear())
                .getActualProductQuantity();

        Assertions.assertEquals(productQuantityInNotification , actualShipmentInDeliveryStatementAfterSaving);

    }

    @ParameterizedTest
    @ArgumentsSource(NotificationsWithTheSamePeriodAndProductProvider.class)
    @DisplayName("Проверка связывания в бд строки в ведомости поставки и извещения при его сохранении")
    void Should_Wiring_Notification_And_DeliveryStatementRow_When_Save_Notification(Notification notification1, Notification notification2) {
        DeliveryStatement deliveryStatementBeforeSaving = deliveryStatementService.getAllDeliveryStatementsWithNotifications().get(0);
        for (DeliveryStatement.Row row : deliveryStatementBeforeSaving.getRows()) {
            Assertions.assertEquals(0, row.getNotifications().size());
        }

        notificationService.saveNotification(notification1);
        notificationService.saveNotification(notification2);

        DeliveryStatement deliveryStatementAfterSaving =
                deliveryStatementService.getAllDeliveryStatementsWithNotifications().get(0);

        for (DeliveryStatement.Row row : deliveryStatementAfterSaving.getRows()) {
            if (row.getPeriod() == notification1.getDate().getYear()
                    && row.getProductName().equals(notification1.getProductName())) {
                Assertions.assertEquals(2, row.getNotifications().size());
            } else {
                Assertions.assertEquals(0, row.getNotifications().size());
            }
        }

    }


    static Stream<Notification> returnAllNotifications() {
        return testNotifications.stream();
    }

    static class NotificationsWithTheSamePeriodAndProductProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            HashMap<String, List<Notification>> result = testNotifications.stream()
                    .collect(HashMap::new, (map, not) -> {
                        String key = not.getProductName() + not.getDate().getYear();
                        map.merge(key, new ArrayList<>() {{
                            add(not);
                        }}, (list1, list2) -> {
                            list1.add(not);
                            return list1;
                        });
                    }, HashMap::putAll);
            return result.values().stream()
                    .map(notifications -> Arguments.of(notifications.toArray()));

        }
    }

}
