package work.app.delivery_statement;


import org.springframework.stereotype.Component;
import work.app.exception.DeliverStatementNotFoundException;
import work.app.notification.Notification;

@Component
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
        return deliveryStatementRepository.findByContractNumber(contract)
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
        System.out.println(deliveryStatement.getRows());
        System.out.println(deliveryStatement);
        DeliveryStatement.Row deliveryStatementRow = deliveryStatement
                .getRowByProductAndPeriod(notification.getProductName(), notification.getDate().getYear())
                .orElseThrow( () -> new DeliverStatementNotFoundException(
                        "В ведомости поставки № " + deliveryStatement.getNumber()
                        + " к контракту №" + deliveryStatement.getContractNumber()
                        + " отсутсвует информация об отгурзке изделия " + notification.getProductName()
                        + " в " + notification.getDate().getYear() + " году"));

        deliveryStatement.Row.increaseActualProductQuantity(notification.getDate().getMonth(), notification.getProductQuantity());
        deliveryStatementRepository.save(deliveryStatement);
    }
}
