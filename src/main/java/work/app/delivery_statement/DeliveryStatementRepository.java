package work.app.delivery_statement;

import work.app.delivery_statement.model.DeliveryStatement;

public interface DeliveryStatementRepository {

    boolean saveDeliveryStatement(DeliveryStatement deliveryStatement);


    DeliveryStatement findByContract(String contractNumber);
}
