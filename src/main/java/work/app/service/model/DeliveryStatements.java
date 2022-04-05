package work.app.service.model;


import java.util.*;

public final class DeliveryStatements {
    private DeliveryStatements() {
    }

    public static Map<String, DeliveryStatement> structureByContract(List<DeliveryStatement> deliveryStatements) {
        return deliveryStatements.stream()
                .collect(HashMap::new, (map, ds) -> map.put(ds.getContract().toString(), ds), HashMap::putAll);
    }

    public static Map<String, Set<DeliveryStatement>> structureByProduct(List<DeliveryStatement> deliveryStatements) {
        Map<String, Set<DeliveryStatement>> result = new HashMap<>();
        deliveryStatements.forEach(d -> {
            d.getRows().forEach(row -> {
                result.merge(row.getProductName(), new HashSet<>() {{
                    add(d);
                }}, (set, set2) -> {
                    set.add(d);
                    return set;
                });
            });
        });
        return result;
    }

    public static Map<DeliveryStatement.Row, List<Notification>> structureNotificationsByDeliveryStatementRow(DeliveryStatement deliveryStatement) {
        return deliveryStatement.getRows().stream()
                .collect(HashMap::new, (map, row) ->
                        map.put(row, row.getNotifications()), HashMap::putAll);
    }

    public static Map<Contract, Set<String>> structureProductsByContractForPeriod(List<DeliveryStatement> deliveryStatements, int period) {
        Map <Contract, Set<String>> result = new HashMap<>();
        for (DeliveryStatement ds : deliveryStatements) {
            Set<String> products = ds.getNotDeliveredProductsForPeriod(period);
            if (!products.isEmpty()) {
                result.put(ds.getContract(), products);
            }
        }
        return result;
    }
}

