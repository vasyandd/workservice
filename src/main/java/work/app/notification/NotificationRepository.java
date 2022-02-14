package work.app.notification;

import java.util.Collection;
import java.util.List;

public interface NotificationRepository {
    void saveNotification(Notification notification);

    Collection<Notification> getAllNotifications();

    List<Notification> getAllNotificationsByContactNumber(String s);
}
