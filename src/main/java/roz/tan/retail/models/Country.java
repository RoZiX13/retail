package roz.tan.retail.models;

import jakarta.persistence.*;
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
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "name", length = 128, nullable = false, unique = true)
    private String name;

    @Column(name = "code", length = 3, unique = true)
    private String code;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
}