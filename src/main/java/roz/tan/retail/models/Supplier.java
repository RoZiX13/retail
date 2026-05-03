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
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "supplier_id")
    private Integer supplierId;

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
}
