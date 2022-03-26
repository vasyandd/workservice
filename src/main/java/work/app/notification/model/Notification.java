package work.app.notification.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
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
    private String productNumber;
    private String contractNumber;
    private String additionalAgreement;


    public Notification(Integer number, LocalDate date,
                        String productName, Integer productQuantity,
                        String contractNumber, String agreement, String productNumber) {
        this.number = number;
        this.productNumber = productNumber;
        this.date = date;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.contractNumber = contractNumber;
        this.additionalAgreement = agreement;
    }

}
