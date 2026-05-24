package roz.tan.retail.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import roz.tan.retail.models.Product;

import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("""
            SELECT p FROM Product p WHERE
            (:sku IS NULL OR p.sku = :sku) AND
            (:categoryId IS NULL OR p.category.id = :categoryId) AND
            (:brandId IS NULL OR p.brand.id = :brandId) AND
            (:isActive IS NULL OR p.isActive = :isActive)
            """
    )
    Set<Product> findByFilters(@Param("sku") String sku,
                               @Param("categoryId") Integer categoryId,
                               @Param("brandId") Integer brandId,
                               @Param("isActive") Boolean isActive);

}
