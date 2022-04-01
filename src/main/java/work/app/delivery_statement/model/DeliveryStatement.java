package work.app.delivery_statement.model;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "delivery_statements")
public final class DeliveryStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer number;

    @Embedded
    private Contract contract;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, mappedBy = "deliveryStatement",
            orphanRemoval = true)
    private List<DeliveryStatement.Row> rows;

    public DeliveryStatement(Integer number, Contract contract, List<Row> rows) {
        this.number = number;
        this.contract = contract;
        this.rows = rows;
    }

    public Optional<DeliveryStatement.Row> getRowByProductAndPeriod(String productName, int year) {
        return rows.stream()
                .filter(ds -> ds.getPeriod() == year && ds.getProductName().equals(productName))
                .findFirst();
    }

    @Transient
    public boolean isClosed() {
        return rows.stream().allMatch(Row::isClosed);
    }

    public Set<String> getAllProducts() {
        return rows.stream().map(Row::getProductName).collect(Collectors.toSet());
    }

    public Set<String> getNotDeliveredProducts() {
        return rows.stream().filter(row -> !row.isClosed()).map(Row::getProductName).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "Ведомость поставки № " + number + " по контракту " + contract.toString();
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    @Table(name = "delivery_statement_rows")
    @TypeDef(name = "json", typeClass = JsonStringType.class)
    public static final class Row {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private BigInteger priceForOneProduct;
        private String productName;
        @Type(type = "json")
        @Column(columnDefinition = "character varying")
        private Map<Month, Integer> scheduledShipment;
        @Type(type = "json")
        @Column(columnDefinition = "character varying")
        private Map<Month, Integer> actualShipment;
        private Integer period;
        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "ds_id", nullable = false, updatable = false)
        private DeliveryStatement deliveryStatement;


        public Row(BigInteger priceForOneProduct, String productName, Map<Month, Integer> scheduledShipment,
                   Map<Month, Integer> actualShipment, Integer period) {
            this.priceForOneProduct = priceForOneProduct;
            this.productName = productName;
            this.scheduledShipment = scheduledShipment;
            this.actualShipment = actualShipment;
            this.period = period;
        }

        @Transient
        public int getActualProductQuantity() {
            return actualShipment.values().stream().flatMapToInt(IntStream::of).sum();
        }
        @Transient
        public int getScheduledProductQuantity() {
            return scheduledShipment.values().stream().flatMapToInt(IntStream::of).sum();
        }

        @Transient
        public Map<Month, String> getProductQuantityWithSlash() {
            Map<Month, String> map = new HashMap<>();
            scheduledShipment.keySet()
                    .forEach(k -> {
                        Integer actualQuantity = actualShipment.get(k);
                        map.put(k, scheduledShipment.get(k) + "/" + (actualQuantity == null ? 0 : actualQuantity));
                    });
            return map;
        }

        @Transient
        public boolean isClosed() {
            return getActualProductQuantity() == getScheduledProductQuantity();
        }

        @Transient
        public boolean isLastMonthNow() {
            if (isClosed()) return false;
            int currentMonthCode = LocalDate.now().getMonth().getValue();
            return currentMonthCode == getLastScheduledMonthCode();
        }

        @Transient
        public boolean isExpired() {
            if (isClosed()) return false;
            int currentMonthCode = LocalDate.now().getMonth().getValue();
            return currentMonthCode > getLastScheduledMonthCode();
        }

        @Transient
        private int getLastScheduledMonthCode() {
            return scheduledShipment.keySet().stream()
                    .filter(m -> scheduledShipment.get(m) > 0)
                    .map(Month::getValue)
                    .flatMapToInt(IntStream::of)
                    .max()
                    .getAsInt();
        }

        public void increaseActualProductQuantity(Month month, int quantity) {
            actualShipment.merge(month, quantity, Integer::sum);
        }


    }
}
