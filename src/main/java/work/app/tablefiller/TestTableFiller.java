package work.app.tablefiller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import work.app.service.DeliveryStatementService;
import work.app.service.model.Contract;
import work.app.service.model.DeliveryStatement;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class TestTableFiller  implements CommandLineRunner {


    @Autowired
    private DeliveryStatementService deliveryStatementService;


    public void run(String...args) throws Exception {
      for (int i = 0; i < 1000; i++) {
            List<DeliveryStatement.Row> rowList = new ArrayList<>();
            for (int j = 1; j < 20; j++) {
                int monthKey1 = ThreadLocalRandom.current().nextInt(1, 12);
                int monthKey2 = ThreadLocalRandom.current().nextInt(1, 12);
                int monthKey3 = ThreadLocalRandom.current().nextInt(1, 12);
                int period = ThreadLocalRandom.current().nextInt(2020, 2025);
                HashMap<Month, Integer> shipment = new HashMap<>();
                shipment.put(Month.of(monthKey1), 500);
                shipment.put(Month.of(monthKey2), 100);
                shipment.put(Month.of(monthKey3), 78);
                DeliveryStatement.Row row = new DeliveryStatement.Row(new BigInteger("12313213"),
                        "Korpus" + j, shipment, period);
                rowList.add(row);
            }
            DeliveryStatement deliveryStatement = new DeliveryStatement(5,
                    new Contract("Contract" + i, LocalDate.now(), 3),
                    rowList);
            deliveryStatementService.saveDeliveryStatement(deliveryStatement);
      }
    }
}
