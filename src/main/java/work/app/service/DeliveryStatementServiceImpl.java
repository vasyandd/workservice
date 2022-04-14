package work.app.service;


import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.app.repository.DeliveryStatementRepository;
import work.app.service.model.Contract;
import work.app.service.model.DeliveryStatement;
import work.app.service.model.Notification;

import java.util.List;
import java.util.stream.Collectors;

import static work.app.service.model.DeliveryStatement.Row;

@Service
public class DeliveryStatementServiceImpl implements DeliveryStatementService {
    private final DeliveryStatementRepository deliveryStatementRepository;

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
    public void deleteAll() {
        deliveryStatementRepository.deleteAll();
    }

    @Transactional
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
    public List<DeliveryStatement> getAllDeliveryStatementWithoutNotifications() {
        return deliveryStatementRepository.findAll();
    }

    @Transactional
    @Override
    public List<DeliveryStatement> getAllDeliveryStatementsWithNotifications() {
        List<DeliveryStatement> result = deliveryStatementRepository.findAll();
        for (DeliveryStatement ds : result) {
            for (Row row : ds.getRows()) {
                Hibernate.initialize(row.getNotifications());
            }
        }
        return result;
    }

    @Override
    public List<DeliveryStatement> getOpenDeliveryStatements() {
        return getAllDeliveryStatementWithoutNotifications().stream().filter(d -> !d.isClosed()).collect(Collectors.toList());
    }
}
