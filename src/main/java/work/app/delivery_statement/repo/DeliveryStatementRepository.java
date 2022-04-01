package work.app.delivery_statement.repo;


import org.springframework.data.repository.CrudRepository;
import work.app.delivery_statement.model.Contract;
import work.app.delivery_statement.model.DeliveryStatement;

import java.util.Optional;

public interface DeliveryStatementRepository extends CrudRepository<DeliveryStatement, Long> {

    Optional<DeliveryStatement> findByContract(Contract contract);
//
//    @Query("SELECT d FROM DeliveryStatement d JOIN FETCH d.rows JOIN fetch Notification ")
//    List<DeliveryStatement> findAllWithNotifications();
}
