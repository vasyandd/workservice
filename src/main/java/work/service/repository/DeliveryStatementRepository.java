package work.service.repository;

import work.service.entity.Contract;
import work.service.entity.DeliveryStatement;
import work.service.entity.Product;

import java.util.Collection;

public interface DeliveryStatementRepository {

    DeliveryStatement addDeliveryStatement(DeliveryStatement deliveryStatement);

    Collection<DeliveryStatement> getDeliveryStatementsByContract(Contract contract);

    Collection<DeliveryStatement> getDeliveryStatementsByProduct(Product product);


}
