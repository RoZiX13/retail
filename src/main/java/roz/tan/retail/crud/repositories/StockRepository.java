package roz.tan.retail.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import roz.tan.retail.models.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer> {
}