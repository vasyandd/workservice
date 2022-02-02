package work.app.notification;

import java.util.Collection;

public interface NotificationRepository {
    void saveNotification(Notification notification);

    Collection<Notification> getAllNotifications();
}
