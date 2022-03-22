package work.app.notification;

import org.springframework.stereotype.Component;
import work.app.delivery_statement.DeliveryStatementService;

import java.util.List;

@Component
public class NotificationServiceImpl implements NotificationService {
    private NotificationRepository notificationRepository;
    private DeliveryStatementService deliveryStatementService;

    public NotificationServiceImpl(DeliveryStatementService deliveryStatementService, NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
        this.deliveryStatementService = deliveryStatementService;
    }

    @Override
    public void saveNotification(Notification notification) {
        deliveryStatementService.updateDeliveryStatement(notification);
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return null;
    }
}
