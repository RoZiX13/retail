package roz.tan.retail.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import roz.tan.retail.models.Supplier;

import java.util.List;
import java.util.Set;

public interface SupplierRepository
        extends JpaRepository<Supplier, Integer> {

    @Query("""
            SELECT sup
            FROM Supplier sup
            WHERE sup.id IN :ids
            """)
    Set<Supplier> findAllByIds(@Param("ids") List<Integer> ids);

    Set<Supplier> findAllByIsActive(boolean active);

}
