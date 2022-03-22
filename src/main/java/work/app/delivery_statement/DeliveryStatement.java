package work.app.delivery_statement;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Entity
@Table(name = "delivery_statement")
public class DeliveryStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contractNumber;

    private LocalDate contractDate;

    private Integer number;

    private String additionalAgreement;

    private boolean isClosed;

    public DeliveryStatement(String contractNumber, LocalDate contractDate,
                             Integer number, String additionalAgreement, List<DeliveryStatement.Row> rows) {
        this.contractNumber = contractNumber;
        this.contractDate = contractDate;
        this.number = number;
        this.additionalAgreement = additionalAgreement;
        this.rows = rows;
    }

    public DeliveryStatement() {
    }

    @Column(columnDefinition = "jsonb")
    private List<DeliveryStatement.Row> rows = new ArrayList<>();

    public LocalDate getContractDate() {
        return contractDate;
    }

    public void setContractDate(LocalDate contractDate) {
        this.contractDate = contractDate;
    }
    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<DeliveryStatement.Row> getDeliveryStatementRows() {
        return rows;
    }

    public void addRow(DeliveryStatement.Row row) {
        rows.add(row);
    }

    public void addRows(List<DeliveryStatement.Row> rows) {
        this.rows.addAll(rows);
    }

    public Long getId() {
        return id;
    }

    public String getAdditionalAgreement() {
        return additionalAgreement;
    }

    public void setAdditionalAgreement(String additionalAgreement) {
        this.additionalAgreement = additionalAgreement;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public List<DeliveryStatement.Row> getRows() {
        return rows;
    }

    public void setRows(List<DeliveryStatement.Row> rows) {
        this.rows = rows;
    }

    public Optional<DeliveryStatement.Row> getRowByProductAndPeriod(String productName, int year) {
        return rows.stream()
                .filter(ds -> ds.getPeriod() == year && ds.getProductName().equals(productName))
                //only one row may be with these product and period
                .findFirst();
    }

    public static class Row {
        private BigInteger priceForOneProduct;

        private String productName;

        private int scheduledProductQuantity;

        private int actualProductQuantity;

        private Map<Month, Integer> scheduledShipment;

        private Map<Month, Integer> actualShipment;

        private boolean isClosed;

        private Integer period;

        public Row(BigInteger priceForOneProduct, String productName, int scheduledProductQuantity,
                   int actualProductQuantity, Integer period, Map<Month,
                Integer> scheduledShipment, Map<Month, Integer> actualShipment, boolean isClosed) {
            this.priceForOneProduct = priceForOneProduct;
            this.productName = productName;
            this.scheduledProductQuantity = scheduledProductQuantity;
            this.actualProductQuantity = actualProductQuantity;
            this.scheduledShipment = scheduledShipment;
            this.actualShipment = actualShipment;
            this.isClosed = isClosed;
            this.period = period;
        }

        public BigInteger getPriceForOneProduct() {
            return priceForOneProduct;
        }

        public void setPriceForOneProduct(BigInteger priceForOneProduct) {
            this.priceForOneProduct = priceForOneProduct;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getScheduledProductQuantity() {
            return scheduledProductQuantity;
        }

        public void setScheduledProductQuantity(int scheduledProductQuantity) {
            this.scheduledProductQuantity = scheduledProductQuantity;
        }

        public int getActualProductQuantity() {
            return actualProductQuantity;
        }

        public void setActualProductQuantity(int actualProductQuantity) {
            this.actualProductQuantity = actualProductQuantity;
        }


        public Integer getPeriod() {
            return period;
        }

        public void setPeriod(Integer period) {
            this.period = period;
        }


        public Map<Month, Integer> getScheduledShipment() {
            return scheduledShipment;
        }

        public void setScheduledShipment(Map<Month, Integer> scheduledShipment) {
            this.scheduledShipment = scheduledShipment;
        }

        public Map<Month, Integer> getActualShipment() {
            return actualShipment;
        }

        public void setActualShipment(Map<Month, Integer> actualShipment) {
            this.actualShipment = actualShipment;
        }

        public boolean isClosed() {
            return isClosed;
        }

        public void setClosed(boolean closed) {
            isClosed = closed;
        }


    }
}
