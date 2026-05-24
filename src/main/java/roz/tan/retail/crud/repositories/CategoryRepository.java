package roz.tan.retail.crud.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import roz.tan.retail.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import roz.tan.retail.models.City;

import java.util.List;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("""
           SELECT c
           FROM Category c
           WHERE c.id IN :ids
           """)
    public Set<Category> findAllByIds(@Param("ids") List<Integer> ids);

}