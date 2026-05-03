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
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "customer_id")
    private int customerId;

    @Column(
            name = "first_name",
            length = 64,
            nullable = false
    )
    private String firstName;

    @Column(
            name = "patronymic",
            length = 64
    )
    private String patronymic;

    @Column(
            name = "last_name",
            length = 64,
            nullable = false
    )
    private String lastName;

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
