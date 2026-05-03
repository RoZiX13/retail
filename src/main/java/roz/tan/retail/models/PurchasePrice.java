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
@Table(
        name = "purchase_price",
        uniqueConstraints = {
        @UniqueConstraint(
                name = "uq_purchase_price_period",
                columnNames = {
                        "product_id",
                        "supplier_id",
                        "valid_from"
                })
})
public class PurchasePrice {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "purchase_price_id")
    private int purchasePriceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_id",
            nullable = false
    )
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "supplier_id",
            nullable = false
    )
    private Supplier supplier;

    @Column(
            name = "price_value",
            precision = 12,
            scale = 2,
            nullable = false
    )
    private double priceValue;

    @Column(
            name = "valid_from",
            nullable = false,
            columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "valid_to",
            columnDefinition = "TIMESTAMPTZ"
    )
    private LocalDateTime validTo;

    @Column(
            name = "created_at",
            columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP"
    )
    private LocalDateTime createdAt;
}
