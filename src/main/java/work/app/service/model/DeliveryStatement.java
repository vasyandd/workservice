package work.app.service.model;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "DELIVERY_STATEMENTS")
public final class DeliveryStatement {

    @Id
    @GeneratedValue
    private Long id;
    private Integer number;
    private Contract contract;
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true,
            mappedBy = "deliveryStatement", cascade = CascadeType.ALL)
    private List<DeliveryStatement.Row> rows = new ArrayList<>();

    public DeliveryStatement(Integer number, Contract contract, List<Row> rows) {
        this.number = number;
        this.contract = contract;
        this.rows = rows;
    }


    public Row getRowByProductAndPeriod(String productName, int year) {
        return rows.stream()
                .filter(ds -> ds.getPeriod() == year && ds.getProductName().equals(productName))
                .findFirst()
                .get();
    }

    public boolean isClosed() {
        return rows.stream().allMatch(Row::isClosed);
    }

    public Map<Integer, List<Row>> getNotDeliveredProductsByPeriod() {
        return rows.stream()
                .filter(row -> !row.isClosed())
                .collect(groupingBy(Row::getPeriod));
    }

    @Override
    public String toString() {
        return "Ведомость поставки" + (number != null ? " № " + number : "")
                + " к контракту " + contract.toString();
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    @Table(name = "DELIVERY_STATEMENT_ROWS")
    @TypeDef(name = "json", typeClass = JsonStringType.class)
    public static final class Row {

        @Id
        @GeneratedValue
        private Long id;
        private BigInteger priceForOneProduct;
        private String productName;

        @Type(type = "json")
        @Column(columnDefinition = "varchar")
        private Map<Month, Integer> scheduledShipment;

        @Type(type = "json")
        @Column(columnDefinition = "varchar")
        private Map<Month, Integer> actualShipment = new HashMap<>();
        private Integer period;

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "ds_id")
        private DeliveryStatement deliveryStatement;

        @NotFound(action = NotFoundAction.IGNORE)
        @OneToMany(fetch = FetchType.LAZY,
                mappedBy = "deliveryStatementRow", orphanRemoval = true)
        private List<Notification> notifications;

        public Row(BigInteger priceForOneProduct, String productName, Map<Month, Integer> scheduledShipment, Integer period) {
            this.priceForOneProduct = priceForOneProduct;
            this.productName = productName;
            this.scheduledShipment = scheduledShipment;
            this.period = period;
        }

        @Transient
        public int getActualProductQuantity() {
            return actualShipment.values().stream()
                    .flatMapToInt(IntStream::of)
                    .sum();
        }

        @Transient
        public int getScheduledProductQuantity() {
            return scheduledShipment.values().stream()
                    .flatMapToInt(IntStream::of)
                    .sum();
        }

        @Transient
        public Map<Month, String> getProductQuantityWithSlash() {
            Map<Month, String> map = new HashMap<>();
            for (Month month : Month.values()) {
                Integer scheduledQuantity = scheduledShipment.get(month);
                Integer actualQuantity = actualShipment.get(month);
                map.put(month, (scheduledQuantity != null ? scheduledQuantity : 0)
                        + "/" + (actualQuantity != null ? actualQuantity : 0));
            }
            return map;
        }

        @Transient
        public boolean isClosed() {
            return getActualProductQuantity() >= getScheduledProductQuantity();
        }

        @Transient
        public boolean isLastMonthNow() {
            if (isClosed()) return false;
            int currentMonthCode = LocalDate.now().getMonth().getValue();
            int currentYear = LocalDate.now().getYear();
            return period == currentYear
                    && currentMonthCode == getLastScheduledMonthCode();
        }

        @Transient
        public boolean isExpired() {
            if (isClosed()) return false;
            int currentMonthCode = LocalDate.now().getMonth().getValue();
            int currentYear = LocalDate.now().getYear();
            return period <= currentYear
                    && currentMonthCode > getLastScheduledMonthCode();
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

        public void addNotification(Notification notification) {
            notifications.add(notification);
        }
    }
}
