package roz.tan.retail.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import roz.tan.retail.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
