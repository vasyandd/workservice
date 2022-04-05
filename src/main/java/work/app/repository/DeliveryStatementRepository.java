package work.app.repository;


import org.springframework.data.repository.CrudRepository;
import work.app.service.model.Contract;
import work.app.service.model.DeliveryStatement;

public interface DeliveryStatementRepository extends CrudRepository<DeliveryStatement, Long> {

   DeliveryStatement findByContract(Contract contract);

}
