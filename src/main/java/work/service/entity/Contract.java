package work.service.entity;

import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "contract")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String number;

    @Temporal(value = TemporalType.DATE)
    private LocalDate dateOfConclusion;
    private Contract parentContract;

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
}
