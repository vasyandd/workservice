package work.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import work.app.service.model.DeliveryStatement;
import work.app.service.model.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByDeliveryStatementRow(DeliveryStatement.Row row);

}
