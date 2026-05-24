package roz.tan.retail.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import roz.tan.retail.models.Brand;

import java.util.List;
import java.util.Set;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

    @Query("""
            SELECT b
            FROM Brand b
            WHERE b.id IN :ids
            """)
    Set<Brand> findAllByIds(@Param("ids") List<Integer> ids);

}
