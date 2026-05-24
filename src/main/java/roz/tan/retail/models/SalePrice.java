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
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "sale_price",
        uniqueConstraints = {
        @UniqueConstraint(
                name = "uq_sale_price_period",
                columnNames = {
                        "product_id",
                        "shop_id",
                        "valid_from"
                })
})
public class SalePrice {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "sale_price_id")
    private int salePriceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_id",
            nullable = false
    )
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "shop_id",
            nullable = false
    )
    private Shop shop;

    @Column(name = "price_value",
            nullable = false
    )
    private double priceValue;

    @Column(
            name = "discount_amount",
            columnDefinition = "DECIMAL(12,2) DEFAULT 0"
    )
    private double discountAmount;

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
        SalePrice salePrice = (SalePrice) o;
        return salePriceId == salePrice.salePriceId && Double.compare(priceValue, salePrice.priceValue) == 0 && Double.compare(discountAmount, salePrice.discountAmount) == 0 && Objects.equals(validFrom, salePrice.validFrom) && Objects.equals(validTo, salePrice.validTo) && Objects.equals(createdAt, salePrice.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salePriceId, priceValue, discountAmount, validFrom, validTo, createdAt);
    }
}
