package roz.tan.retail.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import roz.tan.retail.models.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
