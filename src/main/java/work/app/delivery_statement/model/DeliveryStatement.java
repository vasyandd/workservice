package work.app.delivery_statement.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import work.app.delivery_statement.entity.DeliveryStatementEntity;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public final class DeliveryStatement {

    private Long id;
    private String contractNumber;
    private LocalDate contractDate;
    private Integer number;
    private Integer additionalAgreement;
    private boolean isClosed;
    private List<DeliveryStatement.Row> rows;

    public Optional<DeliveryStatement.Row> getRowByProductAndPeriod(String productName, int year) {
        return rows.stream()
                .filter(ds -> ds.getPeriod() == year && ds.getProductName().equals(productName))
                .findFirst();
    }

    public void checkIsClosed() {
        isClosed = rows.stream().allMatch(Row::isCompleted);
    }

    public static DeliveryStatementEntity toEntity(DeliveryStatement deliveryStatement){
        ObjectMapper mapper = new ObjectMapper();
        List<String> rows = deliveryStatement.getRows().stream()
                .map(r -> {
                    try {
                        return mapper.writeValueAsString(r);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Что-то пошло не так при маппинге строки из ведомости поставки в String");
                    }
                })
                .collect(Collectors.toList());
        return new DeliveryStatementEntity(deliveryStatement.id, deliveryStatement.contractNumber,
                deliveryStatement.contractDate, deliveryStatement.number, deliveryStatement.additionalAgreement,
                deliveryStatement.isClosed, rows);
    }

    public static DeliveryStatement toModel(DeliveryStatementEntity entity) {
        ObjectMapper mapper = new ObjectMapper();
        List<DeliveryStatement.Row> rows = entity.getRows().stream()
                .map(r -> {
                    try {
                        return mapper.readValue(r, Row.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Что-то пошло не так при маппинге строки " +
                                "ведомости поставки из JSON в объект");
                    }
                }).collect(Collectors.toList());
        return new DeliveryStatement(entity.getId(), entity.getContractNumber(), entity.getContractDate(),
                entity.getNumber(), entity.getAdditionalAgreement(), entity.isClosed(), rows);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Row {
        private BigInteger priceForOneProduct;
        private String productName;
        private Map<Month, Integer> scheduledShipment;
        private Map<Month, Integer> actualShipment;
        private boolean isCompleted;
        private Integer period;



        public int actualProductQuantity() {
            return actualShipment.values().stream().flatMapToInt(IntStream::of).sum();
        }

        public int scheduledProductQuantity() {
            return scheduledShipment.values().stream().flatMapToInt(IntStream::of).sum();
        }

        public void increaseActualProductQuantity(Month month, int quantity) {
            actualShipment.merge(month, quantity, Integer::sum);
            if (actualProductQuantity() == scheduledProductQuantity()) isCompleted = true;
        }

    }
}
