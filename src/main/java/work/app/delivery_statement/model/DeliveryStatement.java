package work.app.delivery_statement.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "delivery_statement")
public class DeliveryStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contractNumber;

    private LocalDate contractDate;

    private Integer number;

    private String additionalAgreement;

    private boolean isClosed;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="delivery_statement_row", joinColumns=@JoinColumn(name="DS_ID"))
    private List<DeliveryStatementRow> rows = new ArrayList<>();

    public LocalDate getContractDate() {
        return contractDate;
    }

    public void setContractDate(LocalDate contractDate) {
        this.contractDate = contractDate;
    }
    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<DeliveryStatementRow> getDeliveryStatementRows() {
        return rows;
    }

    public void addRow(DeliveryStatementRow row) {
        rows.add(row);
    }

    public Long getId() {
        return id;
    }

    public String getAdditionalAgreement() {
        return additionalAgreement;
    }

    public void setAdditionalAgreement(String additionalAgreement) {
        this.additionalAgreement = additionalAgreement;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public List<DeliveryStatementRow> getRows() {
        return rows;
    }

    public void setRows(List<DeliveryStatementRow> rows) {
        this.rows = rows;
    }

    public DeliveryStatementRow getRowByProductAndPeriod(String productName, int year) {
        return rows.stream()
                .filter(ds -> ds.getPeriod() == year && ds.getProductName().equals(productName))
                //only one row may be with these product and period
                .findFirst()
                .get();
    }
}
