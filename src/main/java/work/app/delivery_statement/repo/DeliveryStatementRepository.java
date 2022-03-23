package work.app.delivery_statement.repo;


import org.springframework.data.repository.CrudRepository;
import work.app.delivery_statement.entity.DeliveryStatementEntity;

import java.util.Optional;

public interface DeliveryStatementRepository extends CrudRepository<DeliveryStatementEntity, Long> {

    Optional<DeliveryStatementEntity> findByContractNumberAndAdditionalAgreement(String contractNumber, String agreement);
}
