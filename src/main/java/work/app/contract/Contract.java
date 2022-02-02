package work.app.contract;

import work.app.product.Product;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contract")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String number;

    private LocalDate dateOfConclusion;

    @OneToOne
    @JoinColumn(name = "parent_id")
    private Contract parentContract;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Product> products = new ArrayList<>();

    public Contract() {
    }

    public Contract(String number, LocalDate dateOfConclusion, Contract parentContract) {
        this.number = number;
        this.dateOfConclusion = dateOfConclusion;
        this.parentContract = parentContract;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDateOfConclusion() {
        return dateOfConclusion;
    }

    public void setDateOfConclusion(LocalDate dateOfConclusion) {
        this.dateOfConclusion = dateOfConclusion;
    }

    public Contract getParentContract() {
        return parentContract;
    }

    public void setParentContract(Contract parentContract) {
        this.parentContract = parentContract;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
