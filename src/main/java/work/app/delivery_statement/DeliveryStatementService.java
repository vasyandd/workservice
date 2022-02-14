package work.app.delivery_statement;

import work.app.delivery_statement.model.DeliveryStatement;
import work.app.notification.Notification;

public interface DeliveryStatementService {
    void saveDeliveryStatement(DeliveryStatement deliveryStatement);

    void updateDeliveryStatement(Notification notification);

    DeliveryStatement getDeliveryStatementByContract(String contract);
}
