package work.app.notification;

import java.util.Collection;

public class NotificationServiceImpl implements NotificationService {
    private NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void saveNotification(NotificationDto notification) {

    }

    @Override
    public Collection<Notification> getAllNotifications() {
        return notificationRepository.getAllNotifications();
    }
}
