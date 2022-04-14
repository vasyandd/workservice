package work.app.service.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Contract {
    private String number;
    private LocalDate date;
    private Integer additionalAgreement;

    @Override
    public String toString() {
        return "№ " + number + " от " + date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                + (additionalAgreement != null ? " д.c. " + additionalAgreement : "");
    }
}
