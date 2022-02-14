import mock.DeliveryStatementRepositoryMock;
import mock.NotificationRepositoryMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import work.app.delivery_statement.DeliveryStatementService;
import work.app.delivery_statement.DeliveryStatementServiceImpl;
import work.app.delivery_statement.model.DeliveryStatement;
import work.app.delivery_statement.model.DeliveryStatementRow;
import work.app.notification.Notification;
import work.app.notification.NotificationService;
import work.app.notification.NotificationServiceImpl;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;

public class DeliveryStatementRepositoryImplTest {
    private static NotificationRepositoryMock notificationRepository;
    private static DeliveryStatementRepositoryMock deliveryStatementRepository;
    private static NotificationService notificationService;
    private static DeliveryStatementService deliveryStatementService;
    private Notification notification;
    private DeliveryStatement deliveryStatement;

    @BeforeClass
    public static void setUp() {
        notificationRepository = new NotificationRepositoryMock();
        deliveryStatementRepository = new DeliveryStatementRepositoryMock();
        deliveryStatementService = new DeliveryStatementServiceImpl(deliveryStatementRepository);
        notificationService = new NotificationServiceImpl(notificationRepository, deliveryStatementService);
    }

    @Before
    public void setTestData() {
        notificationRepository.clear();
        deliveryStatementRepository.clear();
        notification = new Notification();
        notification.setContractNumber("202332140294320402432/434-1");
        notification.setDate(LocalDate.of(2022, Month.AUGUST, 25));
        notification.setNumber(131);
        notification.setProductName("Korpus_1");
        notification.setProductQuantity(4);

        deliveryStatement = new DeliveryStatement();
        deliveryStatement.setContractNumber("202332140294320402432/434-1");
        deliveryStatement.setContractDate(LocalDate.of(2021, Month.AUGUST, 25));
        deliveryStatement.setNumber(2);
        DeliveryStatementRow deliveryStatementRow_1 = new DeliveryStatementRow();
        deliveryStatementRow_1.setPeriod(2022);
        deliveryStatementRow_1.setPriceForOneProduct(new BigInteger("23321312"));
        deliveryStatementRow_1.setNote("zaebumba");
        deliveryStatementRow_1.setProductName("Korpus_1");
        deliveryStatementRow_1.setScheduledProductQuantity(35);
        //sum of quantity for every month must be equal to scheduledProductQuantity
        deliveryStatementRow_1.setScheduledShipment(new HashMap<Month, Integer>() {{
            put(Month.JANUARY, 0);
            put(Month.FEBRUARY, 5);
            put(Month.MARCH, 0);
            put(Month.APRIL, 5);
            put(Month.MAY,11);
            put(Month.JUNE, 9);
            put(Month.JULY, 2);
            put(Month.AUGUST, 0);
            put(Month.SEPTEMBER, 1);
            put(Month.OCTOBER, 1);
            put(Month.NOVEMBER, 1);
            put(Month.DECEMBER, 0);
        }});
        deliveryStatementRow_1.setActualShipment(new HashMap<Month, Integer>() {{
            put(Month.JANUARY, 0);
            put(Month.FEBRUARY, 0);
            put(Month.MARCH, 0);
            put(Month.APRIL, 0);
            put(Month.MAY,0);
            put(Month.JUNE, 0);
            put(Month.JULY, 0);
            put(Month.AUGUST, 0);
            put(Month.SEPTEMBER, 0);
            put(Month.OCTOBER, 0);
            put(Month.NOVEMBER, 0);
            put(Month.DECEMBER, 0);
        }});
//        DeliveryStatementRow deliveryStatementRow_2 = new DeliveryStatementRow();
//        deliveryStatementRow_2.setPeriod(2022);
//        deliveryStatementRow_2.setPriceForOneProduct(new BigInteger("23321312"));
//        deliveryStatementRow_2.setNote("zaebumba");
//        deliveryStatementRow_2.setScheduledProductQuantity(31);
//        deliveryStatementRow_2.setProductName("Korpus_1");
        //sum of quantity for every month must be equal to scheduledProductQuantity
//        deliveryStatementRow_2.setScheduledShipment(new HashMap<Month, Integer>() {{
//            put(Month.JANUARY, 0);
//            put(Month.FEBRUARY, 1);
//            put(Month.MARCH, 0);
//            put(Month.APRIL, 0);
//            put(Month.MAY,16);
//            put(Month.JUNE, 9);
//            put(Month.JULY, 2);
//            put(Month.AUGUST, 0);
//            put(Month.SEPTEMBER, 1);
//            put(Month.OCTOBER, 1);
//            put(Month.NOVEMBER, 1);
//            put(Month.DECEMBER, 0);
//        }});
        deliveryStatement.addRow(deliveryStatementRow_1);

        deliveryStatementService.saveDeliveryStatement(deliveryStatement);

//        deliveryStatement.addRow(deliveryStatementRow_2);
    }

    @Test
    public void whenSaveNotification_deliveryStatementShouldBeChanged() {
        notificationService.saveNotification(notification);
        DeliveryStatement updatedDeliveryStatement = deliveryStatementService.getDeliveryStatementByContract(notification.getContractNumber());
        int actualQuantity = updatedDeliveryStatement.getRows().get(0).getActualProductQuantity();
        int actualQuantityInMonth = updatedDeliveryStatement
                .getRows()
                .get(0)
                .getActualShipment()
                .get(notification.getDate().getMonth());
        Assert.assertEquals(notification.getProductQuantity().intValue(),actualQuantity);
        Assert.assertEquals(notification.getProductQuantity().intValue(),actualQuantityInMonth);
    }




}
