package roz.tan.retail.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

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
@Table(name = "position")
public class Position {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "position_id")
    private int positionId;

    @Column(
            name = "name",
            length = 128,
            nullable = false,
            unique = true
    )
    private String name;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return positionId == position.positionId
                && Objects.equals(name, position.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                positionId,
                name
        );
    }
}
