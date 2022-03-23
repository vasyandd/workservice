package work.app.notification.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import work.app.notification.model.Notification;

import java.util.Collection;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Collection<Notification> findAllByContractNumber(String contractNumber);
    
}
