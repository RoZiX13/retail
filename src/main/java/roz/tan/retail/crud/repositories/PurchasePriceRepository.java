package roz.tan.retail.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import roz.tan.retail.models.PurchasePrice;

public interface PurchasePriceRepository extends JpaRepository<PurchasePrice, Integer> {
}
