package work.app.service;


import work.app.service.model.Notification;

import java.util.List;

public interface NotificationService {

    void saveNotification(Notification notification);

    List<Notification> getAllNotifications();

}
