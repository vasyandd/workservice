package work.app.notification;

import work.app.delivery_statement.DeliveryStatementRepository;
import work.app.delivery_statement.DeliveryStatementService;

import java.util.Collection;

public class NotificationServiceImpl implements NotificationService {
    private NotificationRepository notificationRepository;
    private DeliveryStatementService deliveryStatementService;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void saveNotification(Notification notification) {
        notificationRepository.saveNotification(notification);
        deliveryStatementService.updateDeliveryStatement(notification);
    }

    @Override
    public Collection<Notification> getAllNotifications() {
        return notificationRepository.getAllNotifications();
    }
}
