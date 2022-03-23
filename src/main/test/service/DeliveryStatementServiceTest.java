package service;

import mock.DeliveryStatementRepositoryMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import work.app.delivery_statement.model.DeliveryStatement;
import work.app.delivery_statement.repo.DeliveryStatementRepository;
import work.app.delivery_statement.service.DeliveryStatementService;
import work.app.delivery_statement.service.DeliveryStatementServiceImpl;
import work.app.notification.model.Notification;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;

public class DeliveryStatementServiceTest {
    final DeliveryStatementRepository repository = new DeliveryStatementRepositoryMock();
    final DeliveryStatementService service = new DeliveryStatementServiceImpl(repository);
    // Test data
    final static String KORPUS1 = "KORPUS1";
    final static String KORPUS2 = "KORPUS2";
    final static String CONTRACT12345678 = "12345678";
    final static Notification CLASSIC_NOTIFICATION_ON_KORPUS1 = new Notification(45, LocalDate.of(2022, Month.APRIL, 14),
            "KORPUS1", 4, CONTRACT12345678, "5");
    final static DeliveryStatement.Row KORPUS1_ROW_2022 = new DeliveryStatement.Row(new BigInteger("111111111"), KORPUS1,
            new HashMap<Month, Integer>() {{put(Month.APRIL, 15); put(Month.AUGUST, 16);}}, new HashMap<>(), false, 2022);
    final static DeliveryStatement.Row KORPUS1_ROW_2023 = new DeliveryStatement.Row(new BigInteger("22222222"), KORPUS1,
            new HashMap<Month, Integer>() {{put(Month.APRIL, 15); put(Month.AUGUST, 16);}}, new HashMap<>(), false, 2023);
    final static DeliveryStatement.Row KORPUS2_ROW_2022 = new DeliveryStatement.Row(new BigInteger("22222222"), KORPUS2,
            new HashMap<Month, Integer>() {{put(Month.APRIL, 15); put(Month.AUGUST, 16);}}, new HashMap<>(), false, 2022);
    final static DeliveryStatement.Row KORPUS2_ROW_2023 = new DeliveryStatement.Row(new BigInteger("22222222"), KORPUS2,
            new HashMap<Month, Integer>() {{put(Month.APRIL, 15); put(Month.AUGUST, 16);}}, new HashMap<>(), false, 2023);
    final static DeliveryStatement DELIVERY_STATEMENT_ON_CONTRACT12345678_AGREEMENT5 = new DeliveryStatement(1L, CONTRACT12345678, LocalDate.of(2022, 1, 1),
            5, "5", false, List.of(KORPUS1_ROW_2022, KORPUS1_ROW_2023, KORPUS2_ROW_2022, KORPUS2_ROW_2023));

    @AfterEach
    void clearRepo(){
        repository.deleteAll();
    }

    @BeforeEach
    void addDeliveryStatement() {

    }

    @Test
    void Should_Update_ActualQuantity_From_DeliveryStatement_When_Add_Classic_Notification() {
        int quantityKorpus1BeforeUpdate = DELIVERY_STATEMENT_ON_CONTRACT12345678_AGREEMENT5.getRows().get(0).actualProductQuantity();
        service.saveDeliveryStatement(DELIVERY_STATEMENT_ON_CONTRACT12345678_AGREEMENT5);
        service.updateDeliveryStatement(CLASSIC_NOTIFICATION_ON_KORPUS1);
        int expectedQuantityKorpus1 = quantityKorpus1BeforeUpdate + CLASSIC_NOTIFICATION_ON_KORPUS1.getProductQuantity();
        int quantityKorpus1AfterUpdate = service.getDeliveryStatementByContractAndAdditionalAgreement(CONTRACT12345678, "5")
                .getRows().get(0).actualProductQuantity();
        Assertions.assertEquals(expectedQuantityKorpus1,quantityKorpus1AfterUpdate);
    }
}
