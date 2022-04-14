import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import work.app.WorkServiceSpringBootApplication;
import work.app.service.DeliveryStatements;
import work.app.service.model.Contract;
import work.app.service.model.DeliveryStatement;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest(classes = WorkServiceSpringBootApplication.class)
public class DeliveryStatementsTest {
    private static final List<DeliveryStatement> testDeliveryStatements = new ArrayList<>();

    @BeforeAll
    static void fillTestList() {
        for (int i = 0; i < 20; i++) {
            List<DeliveryStatement.Row> rows = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                int period = ThreadLocalRandom.current().nextInt(2020, 2025);
                int month1 = ThreadLocalRandom.current().nextInt(1, 13);
                int month2 = ThreadLocalRandom.current().nextInt(1, 13);
                int month3 = ThreadLocalRandom.current().nextInt(1, 13);
                Map<Month, Integer> shipment = new HashMap<>();
                shipment.put(Month.of(month1), 37);
                shipment.put(Month.of(month2), 137);
                shipment.put(Month.of(month3), 237);
                DeliveryStatement.Row row = new DeliveryStatement.Row(new BigInteger("1231424"),
                        "Korpus" + j, shipment, period);
                rows.add(row);
            }
            Contract contract = new Contract("1234567/" + i, LocalDate.now(), 2);
            DeliveryStatement deliveryStatement = new DeliveryStatement(4, contract, rows);
            testDeliveryStatements.add(deliveryStatement);
        }
    }

    @Test
    @DisplayName("Проверка метода structureByContract")
    void Check_Working_StructureByContract_Method() {
        Map<String, DeliveryStatement> result = DeliveryStatements.structureByContract(testDeliveryStatements);
        for (DeliveryStatement actualDeliveryStatement : testDeliveryStatements) {
            String actualContract = actualDeliveryStatement.getContract().toString();
            DeliveryStatement expectedDeliveryStatement = result.get(actualContract);
            Assertions.assertEquals(expectedDeliveryStatement, actualDeliveryStatement);
        }
    }

    @Test
    @DisplayName("Проверка метода structureByProduct")
    void Check_Working_StructureByProduct_Method() {
        Map<String, Set<DeliveryStatement>> result = DeliveryStatements.structureByProduct(testDeliveryStatements);
        for (DeliveryStatement actualDeliveryStatement : testDeliveryStatements) {
            for (DeliveryStatement.Row row : actualDeliveryStatement.getRows()) {
                Assertions.assertTrue(result.get(row.getProductName()).contains(actualDeliveryStatement));
            }
        }
    }

    @Test
    @DisplayName("Проверка метода structureProductsByContractForPeriod")
    void Check_Working_StructureProductsByContractForPeriod_Method() {
        Map<Contract, Map<Integer, List<DeliveryStatement.Row>>> result = DeliveryStatements.structureProductsByContractForPeriod(testDeliveryStatements);
        for (DeliveryStatement actualDeliveryStatement : testDeliveryStatements) {
            for (DeliveryStatement.Row row : actualDeliveryStatement.getRows()) {
                Assertions.assertTrue(result.get(actualDeliveryStatement.getContract()).get(row.getPeriod()).contains(row));
            }
        }
    }

}
