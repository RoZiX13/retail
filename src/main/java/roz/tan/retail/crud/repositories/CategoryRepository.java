package roz.tan.retail.crud.repositories;

import roz.tan.retail.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}