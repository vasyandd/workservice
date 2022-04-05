package work.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import work.app.service.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {


}
