package work.app.service;


import org.springframework.stereotype.Component;
import work.app.repository.DeliveryStatementRepository;
import work.app.service.model.Contract;
import work.app.service.model.DeliveryStatement;
import work.app.service.model.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static work.app.service.model.DeliveryStatement.Row;

@Component
public class DeliveryStatementServiceImpl implements DeliveryStatementService {
    private DeliveryStatementRepository deliveryStatementRepository;

    public DeliveryStatementServiceImpl(DeliveryStatementRepository deliveryStatementRepository) {
        this.deliveryStatementRepository = deliveryStatementRepository;
    }


    @Override
    public void saveDeliveryStatement(DeliveryStatement deliveryStatement) {
        deliveryStatement.getRows().forEach(row -> row.setDeliveryStatement(deliveryStatement));
        deliveryStatementRepository.save(deliveryStatement);
    }

    @Override
    public DeliveryStatement getDeliveryStatementsByContract(Contract contract) {
        return deliveryStatementRepository.findByContract(contract);
    }



    @Override
    public void updateDeliveryStatement(Notification notification) {
        DeliveryStatement deliveryStatement = getDeliveryStatementsByContract(notification.getContract());
        Row deliveryStatementRow = deliveryStatement
                .getRowByProductAndPeriod(notification.getProductName(), notification.getDate().getYear());
        deliveryStatementRow.increaseActualProductQuantity(notification.getDate().getMonth(), notification.getProductQuantity());
        deliveryStatementRow.addNotification(notification);
        notification.setDeliveryStatementRow(deliveryStatementRow);
        deliveryStatementRepository.save(deliveryStatement);
    }


    @Override
    public List<DeliveryStatement> getAllDeliveryStatements() {
        List<DeliveryStatement> deliveryStatements = new ArrayList<>();
        deliveryStatementRepository.findAll()
                .iterator()
                .forEachRemaining(deliveryStatements::add);
        return deliveryStatements;
    }

    @Override
    public List<DeliveryStatement> getOpenDeliveryStatements() {
        return getAllDeliveryStatements().stream().filter(d -> !d.isClosed()).collect(Collectors.toList());
    }
}
