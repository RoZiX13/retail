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
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "supplier_id")
    private int supplierId;

    @Column(
            name = "name",
            length = 256,
            nullable = false
    )
    private String name;

    @Column(
            name = "inn",
            length = 12,
            unique = true
    )
    private String inn;

    @Column(
            name = "kpp",
            length = 9
    )
    private String kpp;

    @Column(
            name = "contact_person",
            length = 128
    )
    private String contactPerson;

    @Column(
            name = "phone",
            length = 20
    )
    private String phone;

    @Column(
            name = "email",
            length = 128
    )
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @Column(
            name = "address",
            columnDefinition = "TEXT"
    )
    private String address;

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
        Supplier supplier = (Supplier) o;
        return supplierId == supplier.supplierId
                && isActive == supplier.isActive
                && Objects.equals(name, supplier.name)
                && Objects.equals(inn, supplier.inn)
                && Objects.equals(kpp, supplier.kpp)
                && Objects.equals(contactPerson, supplier.contactPerson)
                && Objects.equals(phone, supplier.phone)
                && Objects.equals(email, supplier.email)
                && Objects.equals(address, supplier.address)
                && Objects.equals(createdAt, supplier.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                supplierId,
                name,
                inn,
                kpp,
                contactPerson,
                phone,
                email,
                address,
                isActive,
                createdAt
        );
    }
}
