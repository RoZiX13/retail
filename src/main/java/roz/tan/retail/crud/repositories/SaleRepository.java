package roz.tan.retail.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import roz.tan.retail.models.Sale;

public interface SaleRepository extends JpaRepository<Sale, Integer> {
}
