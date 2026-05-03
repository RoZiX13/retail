package roz.tan.retail.models;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "city",
        uniqueConstraints = {
        @UniqueConstraint(
                name = "uq_city_country",
                columnNames = {
                        "name",
                        "country_id"
                })
})
public class City {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "city_id")
    private int cityId;

    @Column(
            name = "name",
            length = 64,
            nullable = false
    )
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "country_id",
            nullable = false
    )
    private Country country;

    @Column(
            name = "created_at",
            columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP"
    )
    private LocalDateTime createdAt;
}
