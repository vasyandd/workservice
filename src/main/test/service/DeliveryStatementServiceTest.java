package service;

import mock.DeliveryStatementRepositoryMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import work.app.delivery_statement.model.Contract;
import work.app.delivery_statement.model.DeliveryStatement;
import work.app.delivery_statement.repo.DeliveryStatementRepository;
import work.app.delivery_statement.service.DeliveryStatementService;
import work.app.delivery_statement.service.DeliveryStatementServiceImpl;
import work.app.exception.DeliverStatementNotFoundException;
import work.app.notification.model.Notification;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;

 class DeliveryStatementServiceTest {
    final DeliveryStatementRepository repository = new DeliveryStatementRepositoryMock();
    final DeliveryStatementService service = new DeliveryStatementServiceImpl(repository);
    // Test data
    final static String KORPUS1 = "KORPUS1";
    final static String KORPUS2 = "KORPUS2";
    final static Contract SIMPLE_CONTRACT = new Contract("123456", LocalDate.of(2021, 6, 23), 5);
    final static Contract SIMPLE_CONTRACT_WITHOUT_ADDITIONAL_AGREEMENT = new Contract("123456", LocalDate.of(2021, 6, 23), 0);

    final static Notification CLASSIC_NOTIFICATION_ON_KORPUS1_2022 = new Notification(1L, 45, LocalDate.of(2022, Month.APRIL, 14),
            "KORPUS1", 4, KORPUS1, SIMPLE_CONTRACT);
    final static Notification CLASSIC_NOTIFICATION_ON_KORPUS1_2022_WITHOUT_ADDITIONAL_AGREEMENT = new Notification(1L, 45, LocalDate.of(2022, Month.APRIL, 14),
            "KORPUS1", 4, KORPUS1, SIMPLE_CONTRACT_WITHOUT_ADDITIONAL_AGREEMENT);
    final static Notification CLASSIC_NOTIFICATION_ON_KORPUS1_2029 = new Notification(1L, 45, LocalDate.of(2029, Month.APRIL, 14),
            "KORPUS1", 4, KORPUS1, SIMPLE_CONTRACT);
    final static DeliveryStatement.Row KORPUS1_ROW_2022 = new DeliveryStatement.Row(1L, new BigInteger("111111111"), KORPUS1,
            new HashMap<Month, Integer>() {{put(Month.APRIL, 4);}}, new HashMap<>(), 2022,null);
    final static DeliveryStatement.Row KORPUS1_ROW_2023 = new DeliveryStatement.Row(2L,new BigInteger("22222222"), KORPUS1,
            new HashMap<Month, Integer>() {{put(Month.APRIL, 15); put(Month.AUGUST, 16);}}, new HashMap<>(),  2023,null);
    final static DeliveryStatement.Row KORPUS2_ROW_2022 = new DeliveryStatement.Row(3L,new BigInteger("22222222"), KORPUS2,
            new HashMap<Month, Integer>() {{put(Month.APRIL, 15); put(Month.AUGUST, 16);}}, new HashMap<>(), 2022,null);
    final static DeliveryStatement.Row KORPUS2_ROW_2023 = new DeliveryStatement.Row(4L,new BigInteger("22222222"), KORPUS2,
            new HashMap<Month, Integer>() {{put(Month.APRIL, 15); put(Month.AUGUST, 16);}}, new HashMap<>(), 2023,null);
    final static DeliveryStatement DELIVERY_STATEMENT_ON_CONTRACT12345678_AGREEMENT5 = new DeliveryStatement(1L,5,
            SIMPLE_CONTRACT, List.of(KORPUS1_ROW_2022, KORPUS1_ROW_2023, KORPUS2_ROW_2022, KORPUS2_ROW_2023));
    final static DeliveryStatement DELIVERY_STATEMENT_ON_CONTRACT12345678_WITHOUT_ADDITIONAL_AGREEMENT = new DeliveryStatement(1L,5,
            SIMPLE_CONTRACT_WITHOUT_ADDITIONAL_AGREEMENT, List.of(KORPUS1_ROW_2022, KORPUS1_ROW_2023, KORPUS2_ROW_2022, KORPUS2_ROW_2023));
     final static DeliveryStatement DELIVERY_STATEMENT_ON_CONTRACT12345678_AGREEMENT5_WITH_ONE_ROW = new DeliveryStatement(1L, 45,
             SIMPLE_CONTRACT, List.of(KORPUS1_ROW_2022));

    @AfterEach
    void clearRepo(){
        repository.deleteAll();
    }

    @Test
    void Should_Update_ActualQuantity_From_DeliveryStatement_When_Classic_Notification_Is_Added() {
        int quantityKorpus1BeforeUpdate = DELIVERY_STATEMENT_ON_CONTRACT12345678_AGREEMENT5.getRows().get(0).getActualProductQuantity();
        service.saveDeliveryStatement(DELIVERY_STATEMENT_ON_CONTRACT12345678_AGREEMENT5);
        service.updateDeliveryStatement(CLASSIC_NOTIFICATION_ON_KORPUS1_2022);
        int expectedQuantityKorpus1 = quantityKorpus1BeforeUpdate + CLASSIC_NOTIFICATION_ON_KORPUS1_2022.getProductQuantity();
        int quantityKorpus1AfterUpdate = service.getDeliveryStatementByContract(SIMPLE_CONTRACT)
                .getRows().get(0).getActualProductQuantity();
        Assertions.assertEquals(expectedQuantityKorpus1,quantityKorpus1AfterUpdate);
    }

    @Test
    void Should_Update_ActualQuantity_From_DeliveryStatement_When_Classic_Notification_Without_Additional_Agreement_Is_Added() {
        int quantityKorpus1BeforeUpdate = DELIVERY_STATEMENT_ON_CONTRACT12345678_WITHOUT_ADDITIONAL_AGREEMENT.getRows().get(0).getActualProductQuantity();
        service.saveDeliveryStatement(DELIVERY_STATEMENT_ON_CONTRACT12345678_WITHOUT_ADDITIONAL_AGREEMENT);
        service.updateDeliveryStatement(CLASSIC_NOTIFICATION_ON_KORPUS1_2022_WITHOUT_ADDITIONAL_AGREEMENT);
        int expectedQuantityKorpus1 = quantityKorpus1BeforeUpdate + CLASSIC_NOTIFICATION_ON_KORPUS1_2022_WITHOUT_ADDITIONAL_AGREEMENT.getProductQuantity();
        int quantityKorpus1AfterUpdate = service.getDeliveryStatementByContract(SIMPLE_CONTRACT_WITHOUT_ADDITIONAL_AGREEMENT)
                .getRows().get(0).getActualProductQuantity();
        Assertions.assertEquals(expectedQuantityKorpus1,quantityKorpus1AfterUpdate);
    }

    @Test
    void Should_DeliveryStatement_Is_Closed_When_Classic_Notification_Is_Added_And_All_Rows_Are_Completed() {
        boolean isClosedBeforeUpdate = DELIVERY_STATEMENT_ON_CONTRACT12345678_AGREEMENT5_WITH_ONE_ROW.isClosed();
        service.saveDeliveryStatement(DELIVERY_STATEMENT_ON_CONTRACT12345678_AGREEMENT5_WITH_ONE_ROW);
        service.updateDeliveryStatement(CLASSIC_NOTIFICATION_ON_KORPUS1_2022);
        boolean isClosedAfterUpdate = service.getDeliveryStatementByContract(SIMPLE_CONTRACT)
                .isClosed();
        Assertions.assertNotEquals(isClosedBeforeUpdate,isClosedAfterUpdate);
        Assertions.assertTrue(isClosedAfterUpdate);
    }

    @Test
    void Should_DeliveryStatementNotFoundException_With_Message_When_Classic_Notification_Is_Added_On_NonExistent_Contract() {
        String expectedMessage = "Отсутствует ведомость поставки к нотракту № 123456 от 2021.06.23 д.c. 5";
        DeliverStatementNotFoundException exception = Assertions.assertThrows(DeliverStatementNotFoundException.class,
                () -> service.updateDeliveryStatement(CLASSIC_NOTIFICATION_ON_KORPUS1_2022));
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void Should_DeliveryStatementNotFoundException_With_Message_When_Classic_Notification_Is_Added_On_NonExistent_Period() {
        String expectedMessage = "В ведомости поставки № 5 к контракту № 123456 от 2021.06.23 д.c. 5 отсутсвует информация об отгурзке изделия KORPUS1 в 2029 году";
        service.saveDeliveryStatement(DELIVERY_STATEMENT_ON_CONTRACT12345678_AGREEMENT5);
        DeliverStatementNotFoundException exception = Assertions.assertThrows(DeliverStatementNotFoundException.class,
                () -> service.updateDeliveryStatement(CLASSIC_NOTIFICATION_ON_KORPUS1_2029));
       Assertions.assertEquals(expectedMessage, exception.getMessage());
    }


}
