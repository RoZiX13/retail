package roz.tan.retail.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import roz.tan.retail.models.Unit;

import java.util.List;
import java.util.Set;

public interface UnitRepository extends JpaRepository<Unit, Integer> {

    @Query("""
            SELECT u
            FROM Unit u
            WHERE u.id IN :ids
            """)
    Set<Unit> findAllByIds(@Param("ids") List<Integer> ids);

}
