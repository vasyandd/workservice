package work.app.service;

import org.springframework.stereotype.Service;
import work.app.repository.NotificationRepository;
import work.app.service.model.Notification;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final DeliveryStatementService deliveryStatementService;

    public NotificationServiceImpl(DeliveryStatementService deliveryStatementService, NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
        this.deliveryStatementService = deliveryStatementService;
    }

    @Override
    public void saveNotification(Notification notification) {
        deliveryStatementService.updateDeliveryStatement(notification);
        notificationRepository.save(notification);
    }


}
