package roz.tan.retail.crud.repositories;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import roz.tan.retail.models.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
