package roz.tan.retail.models;

import org.hibernate.annotations.Formula;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "sale_item")
public class SaleItem {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "sale_item_id")
    private long saleItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "sale_id",
            nullable = false
    )
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_id",
            nullable = false
    )
    private Product product;

    @Column(
            name = "quantity",
            nullable = false
    )
    private double quantity;

    @Column(
            name = "unit_price",
            nullable = false
    )
    private double unitPrice;

    @Column(
            name = "discount_amount",
            columnDefinition = "DECIMAL(12,2) DEFAULT 0"
    )
    private double discountAmount;

    // Генерируемое поле – не сохраняется, только для чтения
    @Formula(
            "(quantity * (unit_price - discount_amount))"
    )
    private double finalPrice;

    @Column(
            name = "created_at",
            columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SaleItem saleItem = (SaleItem) o;
        return saleItemId == saleItem.saleItemId && Double.compare(quantity, saleItem.quantity) == 0 && Double.compare(unitPrice, saleItem.unitPrice) == 0 && Double.compare(discountAmount, saleItem.discountAmount) == 0 && Double.compare(finalPrice, saleItem.finalPrice) == 0 && Objects.equals(createdAt, saleItem.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleItemId, quantity, unitPrice, discountAmount, finalPrice, createdAt);
    }
}

