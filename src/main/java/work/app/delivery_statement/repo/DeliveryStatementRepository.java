package work.app.delivery_statement.repo;


import org.springframework.data.repository.CrudRepository;
import work.app.delivery_statement.entity.DeliveryStatementEntity;
import work.app.delivery_statement.model.Contract;

import java.util.Optional;

public interface DeliveryStatementRepository extends CrudRepository<DeliveryStatementEntity, Long> {

    Optional<DeliveryStatementEntity> findByContract(Contract contract);
}
