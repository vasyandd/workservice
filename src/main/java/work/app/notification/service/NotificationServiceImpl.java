package work.app.notification.service;

import org.springframework.stereotype.Component;
import work.app.delivery_statement.model.DeliveryStatement;
import work.app.delivery_statement.service.DeliveryStatementService;
import work.app.notification.model.Notification;
import work.app.notification.repo.NotificationRepository;

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
    public List<Notification> getNotificationsByDeliveryStatementRow(DeliveryStatement.Row row) {
        return notificationRepository.findAllByRowInDeliveryStatement(row);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return null;
    }
}
