package work.app.service;


import work.app.service.model.Contract;
import work.app.service.model.DeliveryStatement;
import work.app.service.model.Notification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class DeliveryStatements {
    private DeliveryStatements() {
    }

    public static Map<String, DeliveryStatement> structureByContract(List<DeliveryStatement> deliveryStatements) {
        return deliveryStatements.stream()
                .collect(Collectors.toMap(d -> d.getContract().toString(), Function.identity()));
    }

    public static Map<String, List<DeliveryStatement>> structureByProduct(List<DeliveryStatement> deliveryStatements) {
        Map<String, List<DeliveryStatement>> result = new HashMap<>();
        for (DeliveryStatement d : deliveryStatements) {
            d.getRows().forEach(row -> {
                result.computeIfAbsent(row.getProductName(),
                        (unused) -> new ArrayList<>()).add(d);
            });
        }
        return result;
    }

    public static Map<DeliveryStatement.Row, List<Notification>> structureNotificationsByDeliveryStatementRow(DeliveryStatement deliveryStatement) {
        return deliveryStatement.getRows().stream()
                .collect(HashMap::new,
                        (map, row) -> map.put(row, row.getNotifications()),
                        HashMap::putAll);
    }

    public static Map<Contract, Map<Integer, List<DeliveryStatement.Row>>> structureProductsByContractForPeriod(List<DeliveryStatement> deliveryStatements) {
        Map<Contract, Map<Integer, List<DeliveryStatement.Row>>> result = new HashMap<>();
        for (DeliveryStatement ds : deliveryStatements) {
            Map<Integer, List<DeliveryStatement.Row>> products = ds.getNotDeliveredProductsByPeriod();
            result.put(ds.getContract(), products);
        }
        return result;
    }
}

