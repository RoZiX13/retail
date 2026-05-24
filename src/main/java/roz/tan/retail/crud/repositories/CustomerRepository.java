package roz.tan.retail.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import roz.tan.retail.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
