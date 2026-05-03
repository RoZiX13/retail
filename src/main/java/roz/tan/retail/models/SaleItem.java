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
            precision = 12,
            scale = 3,
            nullable = false
    )
    private double quantity;

    @Column(
            name = "unit_price",
            precision = 12,
            scale = 2,
            nullable = false
    )
    private double unitPrice;

    @Column(
            name = "discount_amount",
            precision = 12,
            scale = 2,
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
}

