package roz.tan.retail.models;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stock", uniqueConstraints = {
        @UniqueConstraint(
                name = "uq_stock_product_warehouse",
                columnNames = {
                        "product_id", "warehouse_id"
                }
        )
})
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private int stockId;

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

    @Column(
            name = "quantity",
            precision = 12,
            scale = 3,
            nullable = false,
            columnDefinition = "DECIMAL(12,3) DEFAULT 0"
    )
    private double quantity;

    @Column(
            name = "reserved_quantity",
            precision = 12,
            scale = 3,
            nullable = false,
            columnDefinition = "DECIMAL(12,3) DEFAULT 0"
    )
    private double reservedQuantity;

    @Column(
            name = "last_updated",
            columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP"
    )
    private LocalDateTime lastUpdated;

    @Column(
            name = "created_at",
            columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP"
    )
    private LocalDateTime createdAt;
}
