package work.app.delivery_statement;


import work.app.delivery_statement.model.DeliveryStatement;
import work.app.delivery_statement.model.DeliveryStatementRow;
import work.app.notification.Notification;

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
    public DeliveryStatement getDeliveryStatementByContract(String contract) {
        DeliveryStatement deliveryStatement = deliveryStatementRepository.findByContract(contract);
        return deliveryStatement;
    }

    @Override
    public void updateDeliveryStatement(Notification notification) {
        DeliveryStatement deliveryStatement = deliveryStatementRepository.findByContract(notification.getContractNumber());
        DeliveryStatementRow deliveryStatementRow = deliveryStatement.getRowByProductAndPeriod(notification.getProductName(), notification.getDate().getYear());
        deliveryStatementRow.increaseActualProductQuantity(notification.getDate().getMonth(), notification.getProductQuantity());
        System.out.println(deliveryStatementRow.getActualProductQuantity());
        deliveryStatementRepository.saveDeliveryStatement(deliveryStatement);
    }
}
