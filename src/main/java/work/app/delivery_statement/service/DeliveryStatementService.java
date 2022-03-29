package work.app.delivery_statement.service;

import work.app.delivery_statement.model.Contract;
import work.app.delivery_statement.model.DeliveryStatement;
import work.app.notification.model.Notification;

import java.util.List;

public interface DeliveryStatementService {

    void saveDeliveryStatement(DeliveryStatement deliveryStatement);

    void updateDeliveryStatement(Notification notification);

    List<DeliveryStatement> getAllDeliveryStatements();

    List<DeliveryStatement> getOpenDeliveryStatements();

    DeliveryStatement getDeliveryStatementByContract(Contract contract);
}
