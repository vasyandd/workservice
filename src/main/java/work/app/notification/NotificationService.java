package work.app.notification;


import java.util.Collection;

public interface NotificationService {

    void saveNotification(NotificationDto notification);

    Collection<Notification> getAllNotifications();
}
