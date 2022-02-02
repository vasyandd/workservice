package work.app.delivery_statement;

import work.app.contract.Contract;
import work.app.product.Product;

import java.util.Collection;

public interface DeliveryStatementRepository {

    boolean addDeliveryStatement(DeliveryStatement deliveryStatement);

    Collection<DeliveryStatement> getDeliveryStatementsByContract(Contract contract);

    Collection<DeliveryStatement> getDeliveryStatementsByProduct(Product product);


}
