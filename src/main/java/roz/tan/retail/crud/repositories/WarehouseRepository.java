package roz.tan.retail.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import roz.tan.retail.models.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
}
