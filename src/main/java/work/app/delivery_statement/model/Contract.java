package work.app.delivery_statement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Contract {
    private String contractNumber;
    private LocalDate contractDate;
    private Integer additionalAgreement;

    @Override
    public String toString() {
        return "№ " + contractNumber + " от " + contractDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                + " д.c. " + additionalAgreement;
    }
}
