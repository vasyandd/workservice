package work.app.contract;

import java.math.BigInteger;
import java.time.LocalDate;

public class ContractDto {
    private String number;

    private LocalDate dateOfConclusion;

    private BigInteger price;

    private String parentContract;

    private boolean isCompleted;

    private String note;

    public ContractDto() {
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

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public String getParentContract() {
        return parentContract;
    }

    public void setParentContract(String parentContract) {
        this.parentContract = parentContract;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
