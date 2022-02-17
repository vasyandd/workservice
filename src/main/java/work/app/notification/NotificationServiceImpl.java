package work.app.notification;

import work.app.delivery_statement.DeliveryStatementService;

import java.util.List;

public class NotificationServiceImpl implements NotificationService {
    private NotificationRepository notificationRepository;
    private DeliveryStatementService deliveryStatementService;

    public NotificationServiceImpl(NotificationRepository notificationRepository, DeliveryStatementService deliveryStatementService) {
        this.notificationRepository = notificationRepository;
        this.deliveryStatementService = deliveryStatementService;
    }

    @Override
    public void saveNotification(Notification notification) {
     //   notificationRepository.save(notification);
        deliveryStatementService.updateDeliveryStatement(notification);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return null;
    }
}
