package work.service.entity;

import javax.persistence.*;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "delivery_statement")
public class DeliveryStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Contract contract;

    @Column(nullable = false)
    @OneToMany(fetch = FetchType.EAGER)
    private List<Product> products = new ArrayList<>();

    @Column(nullable = false)
    private Shipment shipment;

    public DeliveryStatement(Contract contract, Shipment shipment) {
        this.contract = contract;
        this.shipment = shipment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

}
