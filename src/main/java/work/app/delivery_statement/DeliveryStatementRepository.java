package work.app.delivery_statement;


import work.app.delivery_statement.model.DeliveryStatement;

import java.util.Optional;

public interface DeliveryStatementRepository {

    Optional<DeliveryStatement> findByContract(String contractNumber);
}
