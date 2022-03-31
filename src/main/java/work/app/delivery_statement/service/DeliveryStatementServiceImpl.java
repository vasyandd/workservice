package work.app.delivery_statement.service;


import org.springframework.stereotype.Component;
import work.app.delivery_statement.model.Contract;
import work.app.delivery_statement.model.DeliveryStatement;
import work.app.delivery_statement.repo.DeliveryStatementRepository;
import work.app.exception.DeliverStatementNotFoundException;
import work.app.notification.model.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public DeliveryStatement getDeliveryStatementByContract(Contract contract) {
        return deliveryStatementRepository.findByContract(contract)
                .orElseThrow(() -> new DeliverStatementNotFoundException(
                        "Отсутствует ведомость поставки к нотракту " + contract));
    }

    @Override
    public void updateDeliveryStatement(Notification notification) {
        DeliveryStatement deliveryStatement = getDeliveryStatementByContract(notification.getContract());

        DeliveryStatement.Row deliveryStatementRow = deliveryStatement
                .getRowByProductAndPeriod(notification.getProductName(), notification.getDate().getYear())
                .orElseThrow( () -> new DeliverStatementNotFoundException(
                        "В ведомости поставки № " + deliveryStatement.getNumber()
                        + " к контракту " + deliveryStatement.getContract()
                        + " отсутсвует информация об отгурзке изделия " + notification.getProductName()
                        + " в " + notification.getDate().getYear() + " году"));

        deliveryStatementRow.increaseActualProductQuantity(notification.getDate().getMonth(), notification.getProductQuantity());
        deliveryStatementRow.addNotification(notification);
        notification.setRow(deliveryStatementRow);
        deliveryStatementRepository.save(deliveryStatement);
    }

    @Override
    public List<DeliveryStatement> getAllDeliveryStatementsWithNotifications() {
        return deliveryStatementRepository.findAllWithNotifications();
    }

    @Override
    public List<DeliveryStatement> getAllDeliveryStatements() {
        List<DeliveryStatement> list = new ArrayList<>();
        deliveryStatementRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public List<DeliveryStatement> getOpenDeliveryStatements() {
        return getAllDeliveryStatementsWithNotifications().stream().filter(d -> !d.isClosed()).collect(Collectors.toList());
    }
}
