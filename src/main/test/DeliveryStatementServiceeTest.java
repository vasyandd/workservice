import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import work.app.WorkServiceSpringBootApplication;
import work.app.service.DeliveryStatementService;
import work.app.service.model.Contract;
import work.app.service.model.DeliveryStatement;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest(classes = WorkServiceSpringBootApplication.class)
class DeliveryStatementServiceeTest {
    @Autowired
    static DeliveryStatementService deliveryStatementService;


    @Test
    void testSave() {
        DeliveryStatement deliveryStatement = new DeliveryStatement(5,
                new Contract("123213", LocalDate.now(), 4), new ArrayList<>());
        deliveryStatementService.saveDeliveryStatement(deliveryStatement);
        Assertions.assertEquals(deliveryStatementService.getAllDeliveryStatements().size(), 1);
    }

}
