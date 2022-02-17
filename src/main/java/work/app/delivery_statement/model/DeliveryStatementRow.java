package work.app.delivery_statement.model;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

//@Entity
public class DeliveryStatementRow {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigInteger priceForOneProduct;

    private String productName;

    private int scheduledProductQuantity;

    private int actualProductQuantity;

//    @ElementCollection
//    @MapKeyEnumerated(value = EnumType.STRING)
    private Map<Month, Integer> scheduledShipment;

//    @ElementCollection
//    @MapKeyEnumerated(value = EnumType.STRING)
    private Map<Month, Integer> actualShipment = new HashMap<>();

    private boolean isClosed;

    private String note;

  //  @Column(nullable = false)
    private Integer period;


    public DeliveryStatementRow() {
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

    public void increaseActualProductQuantity(Month month, int quantity) {
        actualProductQuantity += quantity;
        Integer actualQuantityInMonth = actualShipment.get(month);
        actualShipment.put(month, actualQuantityInMonth == null ? 0 : actualQuantityInMonth + quantity);
        if (actualProductQuantity == scheduledProductQuantity) isClosed = true;
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
