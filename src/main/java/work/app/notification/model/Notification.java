package work.app.notification.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import work.app.delivery_statement.model.Contract;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer number;
    private LocalDate date;
    private String productName;
    @Column(nullable = false, name = "product_quantity")
    private Integer productQuantity;
    private String productNumbers;
    @Embedded
    private Contract contract;


    @Override
    public String toString() {
        return productQuantity + " шт. в "
                + date.getMonth().getDisplayName(TextStyle.FULL_STANDALONE , new Locale("ru"))
                + " изв. № " + number + " от " + date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }
}
