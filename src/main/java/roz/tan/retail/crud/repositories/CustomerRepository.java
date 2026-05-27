package roz.tan.retail.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import roz.tan.retail.models.Customer;

import java.util.List;
import java.util.Set;

public interface CustomerRepository
        extends JpaRepository<Customer, Integer> {

    @Query("""
            SELECT cus
            FROM Customer cus
            WHERE cus.id IN :ids
            """)
    Set<Customer> findAllByIds(@Param("ids") List<Integer> ids);

    Set<Customer> findAllByIsActive(boolean isActive);

}
