package work.app.notification.service;


import work.app.notification.model.Notification;

import java.util.List;

public interface NotificationService {

    void saveNotification(Notification notification);

    List<Notification> getAllNotifications();

}
