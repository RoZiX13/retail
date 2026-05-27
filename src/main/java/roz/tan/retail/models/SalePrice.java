package roz.tan.retail.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;

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
@Table(name = "sale_price")
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
            name = "created_at",
            columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SalePrice salePrice = (SalePrice) o;
        return salePriceId == salePrice.salePriceId
                && Double.compare(priceValue, salePrice.priceValue) == 0
                && Double.compare(discountAmount, salePrice.discountAmount) == 0
                && Objects.equals(createdAt, salePrice.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                salePriceId,
                priceValue,
                discountAmount,
                createdAt
        );
    }
}
