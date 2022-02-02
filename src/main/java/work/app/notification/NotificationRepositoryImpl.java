package work.app.notification;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collection;
import java.util.List;

public class NotificationRepositoryImpl implements NotificationRepository {
    private SessionFactory sessionFactory;

    public NotificationRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveNotification(Notification notification) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(notification);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Collection<Notification> getAllNotifications() {
        List<Notification> notifications = null;
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        notifications = session.createQuery("from Notification", Notification.class).list();
        session.getTransaction().commit();
        return notifications;
    }
}
