package work.app.delivery_statement.repo;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import work.app.delivery_statement.model.Contract;
import work.app.delivery_statement.model.DeliveryStatement;

import java.util.List;
import java.util.Optional;

public interface DeliveryStatementRepository extends CrudRepository<DeliveryStatement, Long> {

    Optional<DeliveryStatement> findByContract(Contract contract);

    @Query("SELECT d FROM DeliveryStatement d join Row r JOIN FETCH r.notifications")
    List<DeliveryStatement> findAllWithNotifications();
}
