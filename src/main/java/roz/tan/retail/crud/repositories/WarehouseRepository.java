package roz.tan.retail.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import roz.tan.retail.models.Brand;
import roz.tan.retail.models.Warehouse;

import java.util.List;
import java.util.Set;

public interface WarehouseRepository
        extends JpaRepository<Warehouse, Integer> {

    @Query("""
            SELECT w
            FROM Warehouse w
            WHERE w.id IN :ids
            """)
    Set<Warehouse> findAllByIds(@Param("ids") List<Integer> ids);

}
