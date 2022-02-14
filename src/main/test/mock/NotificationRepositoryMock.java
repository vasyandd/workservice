package mock;

import work.app.notification.Notification;
import work.app.notification.NotificationRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationRepositoryMock implements NotificationRepository {
    private List<Notification> list = new ArrayList<>();

    @Override
    public void saveNotification(Notification notification) {
        list.add(notification);
    }

    @Override
    public Collection<Notification> getAllNotifications() {
        return list;
    }

    @Override
    public List<Notification> getAllNotificationsByContactNumber(String s) {
        return list.stream()
                .filter(n -> n.getContractNumber().equals(s))
                .collect(Collectors.toList());
    }

    public void clear() {
        list.clear();
    }
}
