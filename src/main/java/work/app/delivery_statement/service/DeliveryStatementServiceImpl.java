package work.app.delivery_statement.service;


import org.springframework.stereotype.Component;
import work.app.delivery_statement.model.DeliveryStatement;
import work.app.delivery_statement.repo.DeliveryStatementRepository;
import work.app.exception.DeliverStatementNotFoundException;
import work.app.notification.model.Notification;

@Component
public class DeliveryStatementServiceImpl implements DeliveryStatementService{
    private DeliveryStatementRepository deliveryStatementRepository;

    public DeliveryStatementServiceImpl(DeliveryStatementRepository deliveryStatementRepository) {
        this.deliveryStatementRepository = deliveryStatementRepository;
    }

    @Override
    public void saveDeliveryStatement(DeliveryStatement deliveryStatement) {
        deliveryStatementRepository.save(DeliveryStatement.toEntity(deliveryStatement));
    }

    @Override
    public DeliveryStatement getDeliveryStatementByContractAndAdditionalAgreement(String contract, String agreement) {
        return DeliveryStatement.toModel(deliveryStatementRepository.findByContractNumberAndAdditionalAgreement(contract, agreement)
                .orElseThrow(() -> new DeliverStatementNotFoundException(
                        "Отсутствует ведомость поставки к дополнительному соглашению " + agreement + " к контракту " + contract)));
    }

    @Override
    public void updateDeliveryStatement(Notification notification) {
        DeliveryStatement deliveryStatement = getDeliveryStatementByContractAndAdditionalAgreement(
                notification.getContractNumber(), notification.getAdditionalAgreement());

        DeliveryStatement.Row deliveryStatementRow = deliveryStatement
                .getRowByProductAndPeriod(notification.getProductName(), notification.getDate().getYear())
                .orElseThrow( () -> new DeliverStatementNotFoundException(
                        "В ведомости поставки № " + deliveryStatement.getNumber()
                        + " к контракту №" + deliveryStatement.getContractNumber()
                        + " отсутсвует информация об отгурзке изделия " + notification.getProductName()
                        + " в " + notification.getDate().getYear() + " году"));

        deliveryStatementRow.increaseActualProductQuantity(notification.getDate().getMonth(), notification.getProductQuantity());
        deliveryStatement.checkIsClosed();
        deliveryStatementRepository.save(DeliveryStatement.toEntity(deliveryStatement));
    }
}
