package roz.tan.retail.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PurchasePrice that = (PurchasePrice) o;
        return purchasePriceId == that.purchasePriceId && Double.compare(priceValue, that.priceValue) == 0 && Objects.equals(validFrom, that.validFrom) && Objects.equals(validTo, that.validTo) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchasePriceId, priceValue, validFrom, validTo, createdAt);
    }
}
