package mock;

import work.app.notification.model.Notification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NotificationRepositoryMock  {
    private List<Notification> list = new ArrayList<>();


    public void saveNotification(Notification notification) {
        list.add(notification);
    }


    public Collection<Notification> getAllNotifications() {
        return list;
    }




    public void clear() {
        list.clear();
    }
}
