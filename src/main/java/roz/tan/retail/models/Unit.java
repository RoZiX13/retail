package roz.tan.retail.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "unit")
public class Unit {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "unit_id")
    private int unitId;

    @Column(
            name = "full_name",
            length = 32,
            nullable = false,
            unique = true
    )
    private String fullName;

    @Column(
            name = "short_name",
            length = 8,
            nullable = false,
            unique = true
    )
    private String shortName;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return unitId == unit.unitId && Objects.equals(fullName, unit.fullName) && Objects.equals(shortName, unit.shortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unitId, fullName, shortName);
    }
}
