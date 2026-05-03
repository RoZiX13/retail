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
@Table(name = "stock", uniqueConstraints = {
        @UniqueConstraint(name = "uq_stock_product_warehouse", columnNames = {"product_id", "warehouse_id"})
})
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Integer stockId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(name = "quantity", precision = 12, scale = 3, nullable = false, columnDefinition = "DECIMAL(12,3) DEFAULT 0")
    private BigDecimal quantity;

    @Column(name = "reserved_quantity", precision = 12, scale = 3, nullable = false, columnDefinition = "DECIMAL(12,3) DEFAULT 0")
    private BigDecimal reservedQuantity;

    @Column(name = "last_updated", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime lastUpdated;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
}
