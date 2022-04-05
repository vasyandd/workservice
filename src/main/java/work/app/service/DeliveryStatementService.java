package work.app.service;

import work.app.service.model.Contract;
import work.app.service.model.DeliveryStatement;
import work.app.service.model.Notification;

import java.util.List;

public interface DeliveryStatementService {

    void saveDeliveryStatement(DeliveryStatement deliveryStatement);

    void updateDeliveryStatement(Notification notification);

    List<DeliveryStatement> getAllDeliveryStatements();

    List<DeliveryStatement> getOpenDeliveryStatements();

    DeliveryStatement getDeliveryStatementsByContract(Contract contract);

}
