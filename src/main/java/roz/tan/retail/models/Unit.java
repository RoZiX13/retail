package roz.tan.retail.models;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "unit")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Integer unitId;

    @Column(name = "full_name", length = 32, nullable = false, unique = true)
    private String fullName;

    @Column(name = "short_name", length = 8, nullable = false, unique = true)
    private String shortName;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
}
