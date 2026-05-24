package roz.tan.retail.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import roz.tan.retail.models.SalePrice;

public interface SalePriceRepository extends JpaRepository<SalePrice, Integer> {
}
