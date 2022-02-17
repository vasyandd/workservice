package work.app.notification;



import java.util.Collection;
import java.util.List;

public interface NotificationRepository  {

    Collection<Notification> findAllByContractNumber(String contracNumber);
    
}
