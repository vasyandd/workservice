package work.app.delivery_statement;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "delivery_statement")
public class DeliveryStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contractNumber;

    private LocalDate contractDate;

    private String productName;

    @Column(nullable = false)
    private Integer period;

    private Integer number;

    private BigInteger priceForOneProduct;

    private Integer scheduledProductQuantity;

    private Integer actualProductQuantity;

    @ElementCollection
    @MapKeyEnumerated(value = EnumType.STRING)
    private Map<Month, Integer> scheduledShipment;

    @ElementCollection
    @MapKeyEnumerated(value = EnumType.STRING)
    private Map<Month, Integer> actualShipment = new HashMap<>();

    private boolean isClosed;

    private String note;

    public DeliveryStatement() {
    }

    public Long getId() {
        return id;
    }


    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigInteger getPriceForOneProduct() {
        return priceForOneProduct;
    }

    public void setPriceForOneProduct(BigInteger priceForOneProduct) {
        this.priceForOneProduct = priceForOneProduct;
    }

    public Integer getScheduledProductQuantity() {
        return scheduledProductQuantity;
    }

    public void setScheduledProductQuantity(Integer scheduledProductQuantity) {
        this.scheduledProductQuantity = scheduledProductQuantity;
    }

    public Integer getActualProductQuantity() {
        return actualProductQuantity;
    }

    public void increaseActualProductQuantityBy(Integer quantity) {
        actualProductQuantity += actualProductQuantity;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getContractDate() {
        return contractDate;
    }

    public void setContractDate(LocalDate contractDate) {
        this.contractDate = contractDate;
    }
}
