package work.app.notification;


import java.util.List;

public interface NotificationService {

    void saveNotification(Notification notification);

    List<Notification> getAllNotifications();

}
