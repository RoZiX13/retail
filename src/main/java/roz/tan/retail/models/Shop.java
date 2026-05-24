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
@Table(name = "shop")
public class Shop {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "shop_id")
    private int shopId;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Shop shop = (Shop) o;
        return shopId == shop.shopId && isActive == shop.isActive && Objects.equals(name, shop.name) && Objects.equals(address, shop.address) && Objects.equals(createdAt, shop.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shopId, name, address, isActive, createdAt);
    }
}
