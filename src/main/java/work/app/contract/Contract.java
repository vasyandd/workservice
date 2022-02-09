package work.app.contract;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;

@Entity
@Table(name = "contract")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String number;

    private LocalDate dateOfConclusion;

    private BigInteger price;

    @OneToOne
    @JoinColumn(name = "parent_id")
    private Contract parentContract;

    private boolean isCompleted;

    private String note;

    public Contract() {
    }

    public Contract(String number, LocalDate dateOfConclusion, Contract parentContract, BigInteger price) {
        this.number = number;
        this.dateOfConclusion = dateOfConclusion;
        this.parentContract = parentContract;
        this.price = price;
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

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
