import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import work.app.WorkServiceSpringBootApplication;
import work.app.service.DeliveryStatementService;
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
class DeliveryStatementServiceeTest {

    @Autowired
    private DeliveryStatementService deliveryStatementService;


    @BeforeEach
    void createTestData() {
        for (int i = 0; i < 10; i++) {
            List<DeliveryStatement.Row> rows = new ArrayList<>();
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
            }
            Contract contract = new Contract("1234567/" + i, LocalDate.now(), 2);
            DeliveryStatement deliveryStatement = new DeliveryStatement(4, contract, rows);
            deliveryStatementService.saveDeliveryStatement(deliveryStatement);
        }
    }

    @AfterEach
    void clearTestData(){
       deliveryStatementService.deleteAll();
    }

    @ParameterizedTest
    @MethodSource("createNotifications")
    void Should_Updating_DeliveryStatement_When_Save_Notification(Notification notification) {

    }

    static Stream<Notification> createNotifications() {
        return Stream.of(
                new Notification(1, LocalDate.now(),
                        "dd", 5, "f", null)
        );
    }

}
