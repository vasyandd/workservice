package work.app.notification.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import work.app.delivery_statement.model.Contract;
import work.app.notification.model.Notification;

import java.util.Collection;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Collection<Notification> findAllByContract(Contract contract);
    
}
