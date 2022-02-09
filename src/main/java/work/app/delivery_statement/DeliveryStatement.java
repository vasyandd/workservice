package work.app.delivery_statement;

import work.app.contract.Contract;
import work.app.product.Product;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.Month;
import java.util.*;

@Entity
@Table(name = "delivery_statement")
public class DeliveryStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "contract_id")
    @Column(nullable = false)
    private Contract contract;

    @OneToOne
    @JoinColumn(name = "product_id")
    @Column(nullable = false)
    private Product product;

    @Column(nullable = false)
    private Short period;

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

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Short getPeriod() {
        return period;
    }

    public void setPeriod(Short period) {
        this.period = period;
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

    public void setActualProductQuantity(Integer actualProductQuantity) {
        this.actualProductQuantity = actualProductQuantity;
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
}
