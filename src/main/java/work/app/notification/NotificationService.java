package work.app.notification;


import java.util.Collection;

public interface NotificationService {

    Notification addNotification(Notification notification);

    Collection<Notification> getAllNotifications();
}
