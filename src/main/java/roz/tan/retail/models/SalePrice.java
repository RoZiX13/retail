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
@Table(name = "sale_price", uniqueConstraints = {
        @UniqueConstraint(name = "uq_sale_price_period", columnNames = {"product_id", "shop_id", "valid_from"})
})
public class SalePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_price_id")
    private Integer salePriceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @Column(name = "price_value", precision = 12, scale = 2, nullable = false)
    private BigDecimal priceValue;

    @Column(name = "discount_amount", precision = 12, scale = 2, columnDefinition = "DECIMAL(12,2) DEFAULT 0")
    private BigDecimal discountAmount;

    @Column(name = "valid_from", nullable = false, columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime validFrom;

    @Column(name = "valid_to", columnDefinition = "TIMESTAMPTZ")
    private LocalDateTime validTo;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
}
