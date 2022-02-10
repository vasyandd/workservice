package work.app.delivery_statement;


import work.app.notification.Notification;

import java.time.Month;
import java.util.Map;

public class DeliveryStatementServiceImpl implements DeliveryStatementService{
    private DeliveryStatementRepository deliveryStatementRepository;


    public DeliveryStatementServiceImpl(DeliveryStatementRepository deliveryStatementRepository) {
        this.deliveryStatementRepository = deliveryStatementRepository;

    }

    @Override
    public void saveDeliveryStatement(DeliveryStatement deliveryStatement) {

        deliveryStatementRepository.saveDeliveryStatement(deliveryStatement);
    }

    @Override
    public void updateDeliveryStatement(Notification notification) {
        DeliveryStatement deliveryStatement = deliveryStatementRepository.findByContractNumberAndProductNameAndPeriod(notification.getContractNumber(),
                notification.getProductName(), notification.getDate().getYear());
        deliveryStatement.increaseActualProductQuantityBy(notification.getProductQuantity());
        Map<Month, Integer> actualMap = deliveryStatement.getActualShipment();


    }
}
