package work.app.notification;


import java.util.Collection;

public interface NotificationService {

    void saveNotification(Notification notification);

    Collection<Notification> getAllNotifications();
}
