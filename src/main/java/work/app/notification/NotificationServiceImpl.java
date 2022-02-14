package work.app.notification;

import work.app.delivery_statement.DeliveryStatementService;

public class NotificationServiceImpl implements NotificationService {
    private NotificationRepository notificationRepository;
    private DeliveryStatementService deliveryStatementService;

    public NotificationServiceImpl(NotificationRepository notificationRepository, DeliveryStatementService deliveryStatementService) {
        this.notificationRepository = notificationRepository;
        this.deliveryStatementService = deliveryStatementService;
    }

    @Override
    public void saveNotification(Notification notification) {
        notificationRepository.saveNotification(notification);
        deliveryStatementService.updateDeliveryStatement(notification);
    }


}
