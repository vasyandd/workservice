package work.app.notification;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer number;

    private LocalDate date;

    private String productName;

    @Column(nullable = false, name = "product_quantity")
    private Integer productQuantity;

    private String contractNumber;

    public Notification() {
    }

    public Long getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(number, that.number) &&
                Objects.equals(date, that.date) &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(productQuantity, that.productQuantity) &&
                Objects.equals(contractNumber, that.contractNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, date, productName, productQuantity, contractNumber);
    }
}
