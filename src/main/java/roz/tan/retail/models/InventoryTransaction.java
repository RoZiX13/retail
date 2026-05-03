package roz.tan.retail.models;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.EnumType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import roz.tan.retail.models.enums.ChangeType;
import roz.tan.retail.models.enums.ReferenceType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory_transaction")
public class InventoryTransaction {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "tx_id")
    private long txId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_id",
            nullable = false
    )
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "warehouse_id",
            nullable = false
    )
    private Warehouse warehouse;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "change_type",
            length = 20,
            nullable = false
    )
    private ChangeType changeType;

    @Column(
            name = "quantity_change",
            precision = 12,
            scale = 3,
            nullable = false
    )
    private double quantityChange;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "reference_type",
            length = 30
    )
    private ReferenceType referenceType;

    @Column(name = "reference_id")
    private int referenceId;

    @Column(
            name = "created_at",
            columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP"
    )
    private LocalDateTime createdAt;
}
