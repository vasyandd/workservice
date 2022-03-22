package work.app.delivery_statement;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryStatementRepository extends JpaRepository<DeliveryStatement, Long> {

    Optional<DeliveryStatement> findByContractNumber(String contractNumber);
}
