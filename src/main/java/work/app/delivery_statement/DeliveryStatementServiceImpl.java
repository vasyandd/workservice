package work.app.delivery_statement;


import work.app.exception.DeliverStatementNotFoundException;
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

        deliveryStatementRepository.save(deliveryStatement);
    }

    @Override
    public DeliveryStatement getDeliveryStatementByContract(String contract) {
        return deliveryStatementRepository.findByContract(contract)
                .orElseThrow(() -> new DeliverStatementNotFoundException(
                        "Отсутствует ведомость поставки к контракту " + contract));
    }

    @Override
    public void updateDeliveryStatement(DeliveryStatement deliveryStatement) {
        deliveryStatementRepository.save(deliveryStatement);
    }

    @Override
    public void updateDeliveryStatement(Notification notification) {
        DeliveryStatement deliveryStatement = getDeliveryStatementByContract(notification.getContractNumber());

        DeliveryStatementRow deliveryStatementRow = deliveryStatement
                .getRowByProductAndPeriod(notification.getProductName(), notification.getDate().getYear())
                .orElseThrow( () -> new DeliverStatementNotFoundException(
                        "В ведомости поставки № " + deliveryStatement.getNumber()
                        + " к контракту №" + deliveryStatement.getContractNumber()
                        + " отсутсвует информация об отгурзке изделия " + notification.getProductName()
                        + " в " + notification.getDate().getYear() + " году"));

        deliveryStatementRow.increaseActualProductQuantity(notification.getDate().getMonth(), notification.getProductQuantity());
        deliveryStatementRepository.save(deliveryStatement);
    }
}
