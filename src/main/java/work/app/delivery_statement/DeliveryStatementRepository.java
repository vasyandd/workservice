package work.app.delivery_statement;

public interface DeliveryStatementRepository {

    boolean saveDeliveryStatement(DeliveryStatement deliveryStatement);


    DeliveryStatement findByContractNumberAndProductNameAndPeriod(String contractNumber, String productName, int year);
}
