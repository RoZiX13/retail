package roz.tan.retail.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import roz.tan.retail.models.Sale;
import roz.tan.retail.models.Warehouse;

import java.util.List;
import java.util.Set;

public interface SaleRepository
        extends JpaRepository<Sale, Integer> {

    @Query("""
            SELECT s
            FROM Sale s
            WHERE s.id IN :ids
            """)
    Set<Sale> findAllByIds(@Param("ids") List<Integer> ids);

    @Query("""
            SELECT sal, cust, sh
            FROM Sale sal
            JOIN FETCH sal.customer AS cust
            JOIN FETCH sal.shop AS sh
            WHERE sal.shop.shopId = :shopId AND sal.customer.customerId = :customerId
            """)
    Set<Sale> findAllByShopIdAndCustomerId(
            @Param("shopId") int shopId,
             @Param("shopId") int customerId
            );

}
