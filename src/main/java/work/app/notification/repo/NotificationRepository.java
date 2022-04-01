package work.app.notification.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import work.app.delivery_statement.model.DeliveryStatement;
import work.app.notification.model.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByRowInDeliveryStatement(DeliveryStatement.Row row);
    
}
