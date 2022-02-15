package work.app.delivery_statement;

import org.springframework.data.jpa.repository.JpaRepository;
import work.app.delivery_statement.model.DeliveryStatement;

import java.util.Optional;

public interface DeliveryStatementRepository extends JpaRepository<DeliveryStatement, Long> {

    Optional<DeliveryStatement> findByContract(String contractNumber);
}
