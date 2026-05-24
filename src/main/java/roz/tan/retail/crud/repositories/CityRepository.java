package roz.tan.retail.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import roz.tan.retail.models.City;

import java.util.List;
import java.util.Set;

public interface CityRepository extends JpaRepository<City, Integer> {

    @Query("""
            SELECT c
            FROM City c
            WHERE c.id IN :ids
            """)
    Set<City> findAllByIds(@Param("ids") List<Integer> ids);

}
