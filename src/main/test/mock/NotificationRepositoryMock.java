package mock;

import work.app.notification.model.Notification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationRepositoryMock  {
    private List<Notification> list = new ArrayList<>();


    public void saveNotification(Notification notification) {
        list.add(notification);
    }


    public Collection<Notification> getAllNotifications() {
        return list;
    }


    public List<Notification> getAllNotificationsByContactNumber(String s) {
        return list.stream()
                .filter(n -> n.getContractNumber().equals(s))
                .collect(Collectors.toList());
    }

    public void clear() {
        list.clear();
    }
}
