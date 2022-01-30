package work.service.entity;

import javax.persistence.*;
import java.time.Month;
import java.util.*;

@Entity
@Table(name = "delivery_statement")
public class DeliveryStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private Short period;

    private Integer scheduledProductQuantity;

    private Integer actualProductQuantity;

    @ElementCollection
    @MapKeyEnumerated(value = EnumType.STRING)
    private Map<Month, Short> scheduledShipment = new HashMap<>();

    @ElementCollection
    @MapKeyEnumerated(value = EnumType.STRING)
    private Map<Month, Short> actualShipment = new HashMap<>();

    public DeliveryStatement() {
    }
}
