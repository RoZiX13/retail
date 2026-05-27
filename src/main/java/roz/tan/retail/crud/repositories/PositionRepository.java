package roz.tan.retail.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import roz.tan.retail.models.Position;

import java.util.List;
import java.util.Set;

public interface PositionRepository
        extends JpaRepository<Position, Integer> {

    @Query("""
            SELECT pos
            FROM Position pos
            WHERE pos.id IN :ids
            """)
    Set<Position> findAllByIds(@Param("ids") List<Integer> ids);

}
