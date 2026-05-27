package roz.tan.retail.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import roz.tan.retail.models.Stock;

import java.util.Set;

public interface StockRepository
        extends JpaRepository<Stock, Integer> {

    @Query("""
            SELECT st
            FROM Stock st
            JOIN FETCH st.product prod
            JOIN FETCH st.warehouse war
            WHERE war.warehouseId = :warehouseId AND prod.productId = :productId
            """)
    Set<Stock> findByWarehouseIdAndProductId(
            @Param("warehouseId") int warehouseId,
            @Param("productId") int productId
    );

}
