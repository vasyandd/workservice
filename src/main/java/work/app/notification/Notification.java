package work.app.notification;

import work.app.contract.Contract;
import work.app.product.Product;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer number;

    private LocalDate date;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, name = "product_quantity")
    private Integer productQuantity;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "contract_id")
    private Contract contract;

    public Notification(Integer number, Product product,
                        Integer productQuantity, Contract contract) {
        this.number = number;
        this.product = product;
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
