package roz.tan.retail.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import roz.tan.retail.models.Country;

import java.util.List;
import java.util.Set;

public interface CountryRepository extends JpaRepository<Country, Integer> {

    @Query("""
            SELECT c
            FROM Country c
            WHERE c.id IN :ids
            """)
    Set<Country> findAllByIds(@Param("ids") List<Integer> ids);

}
