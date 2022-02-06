package work.app.delivery_statement;

import java.math.BigInteger;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class DeliveryStatementDto {
    private String contractNumber;
    private String productNumber;
    private Short period;
    private Integer number;
    private BigInteger priceForOneProduct;
    private Integer scheduledProductQuantity;
    private Integer actualProductQuantity;
    private Map<Month, Integer> scheduledShipment = new HashMap<>();
    private Map<Month, Integer> actualShipment = new HashMap<>();
    private boolean isClosed;
    private String note;

    public DeliveryStatementDto() {
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public Short getPeriod() {
        return period;
    }

    public void setPeriod(Short period) {
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
