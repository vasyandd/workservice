package work.app.delivery_statement.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "delivery_statement")
public final class DeliveryStatementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String contractNumber;
    @Column(nullable = false)
    private LocalDate contractDate;
    private Integer number;
    private Integer additionalAgreement;
    @Column(nullable = false)
    private boolean isClosed;

    @Column(length = 500)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "rows", joinColumns = {@JoinColumn(name = "ds_id")})
    private List<String> rows = new ArrayList<>();

}
