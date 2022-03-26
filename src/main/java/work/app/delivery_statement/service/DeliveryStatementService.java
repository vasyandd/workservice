package work.app.delivery_statement.service;

import work.app.delivery_statement.model.DeliveryStatement;
import work.app.notification.model.Notification;

public interface DeliveryStatementService {
    void saveDeliveryStatement(DeliveryStatement deliveryStatement);

    void updateDeliveryStatement(Notification notification);

    DeliveryStatement getDeliveryStatementByContractAndAdditionalAgreement(String contract, String agreement);
}
