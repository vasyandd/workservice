package work.app.delivery_statement;

import work.app.delivery_statement.model.DeliveryStatement;

import java.util.Optional;

public interface DeliveryStatementRepository {

    boolean saveDeliveryStatement(DeliveryStatement deliveryStatement);


    Optional<DeliveryStatement> findByContract(String contractNumber);
}
