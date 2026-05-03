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
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "warehouse_id")
    private int warehouseId;

    @Column(
            name = "name",
            length = 128,
            nullable = false
    )
    private String name;

    @Column(
            name = "address",
            columnDefinition = "TEXT"
    )
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @Column(
            name = "is_active",
            columnDefinition = "BOOLEAN DEFAULT TRUE"
    )
    private boolean isActive;

    @Column(
            name = "created_at",
            columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP"
    )
    private LocalDateTime createdAt;
}
