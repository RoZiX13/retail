package roz.tan.retail.models;

import lombok.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory_transaction")
public class InventoryTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tx_id")
    private Long txId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Enumerated(EnumType.STRING)
    @Column(name = "change_type", length = 20, nullable = false)
    private ChangeType changeType;

    @Column(name = "quantity_change", precision = 12, scale = 3, nullable = false)
    private BigDecimal quantityChange;

    @Enumerated(EnumType.STRING)
    @Column(name = "reference_type", length = 30)
    private ReferenceType referenceType;

    @Column(name = "reference_id")
    private Integer referenceId;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
}
