package work.app.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Collection<Notification> findAllByContractNumber(String contracNumber);
    
}
