package roz.tan.retail.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import roz.tan.retail.models.Position;

public interface PositionRepository extends JpaRepository<Position, Integer> {
}
