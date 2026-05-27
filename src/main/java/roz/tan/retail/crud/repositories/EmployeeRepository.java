package roz.tan.retail.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import roz.tan.retail.models.Brand;
import roz.tan.retail.models.Employee;

import java.util.List;
import java.util.Set;

public interface EmployeeRepository
        extends JpaRepository<Employee, Integer> {

    @Query("""
            SELECT e
            FROM Employee e
            WHERE e.id IN :ids
            """)
    Set<Employee> findAllByIds(@Param("ids") List<Integer> ids);

}
