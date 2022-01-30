package work.service.entity;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Short number;

    private LocalDate date;

    @OneToOne
    private Product product;

    @Column(nullable = false, name = "product_number")
    private String productNumber;

    @Column(nullable = false, name = "product_quantity")
    private String productQuantity;

    @OneToOne
    private Contract contract;

    public Notification(Short number, Product product, String productNumber,
                        String productQuantity, Contract contract) {
        this.number = number;
        this.product = product;
        this.productNumber = productNumber;
        this.productQuantity = productQuantity;
        this.contract = contract;
    }

    public Notification() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getNumber() {
        return number;
    }

    public void setNumber(Short number) {
        this.number = number;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
