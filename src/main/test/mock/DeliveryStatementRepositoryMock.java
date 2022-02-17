package mock;

import work.app.delivery_statement.DeliveryStatementRepository;
import work.app.delivery_statement.model.DeliveryStatement;

import java.util.ArrayList;
import java.util.List;

public class DeliveryStatementRepositoryMock{
    private List<DeliveryStatement> list = new ArrayList<>();


    public boolean save(DeliveryStatement deliveryStatement) {
        return list.add(deliveryStatement);
    }


    public DeliveryStatement findByContract(String contractNumber) {
        return list.stream()
                .filter(ds -> ds.getContractNumber().equals(contractNumber))
                .findAny()
                .get();
    }

    public void clear() {
        list.clear();
    }
}
