package roz.tan.retail.crud.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roz.tan.retail.crud.repositories.EmployeeRepository;
import roz.tan.retail.crud.repositories.PositionRepository;
import roz.tan.retail.models.Employee;
import roz.tan.retail.models.Position;
import roz.tan.retail.utils.dto.EmployeeInput;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class EmployeeService {

    @Autowired
    @Qualifier("employeeRepository")
    private EmployeeRepository employeeRepository;

    @Autowired
    @Qualifier("positionRepository")
    private PositionRepository positionRepository;

    public Set<Employee> findAllByIds(List<Integer> ids) {
        return employeeRepository.findAllByIds(ids);
    }

    @Transactional(readOnly = false)
    public Employee save(EmployeeInput input) {
        // 1. Валидация обязательных полей
        if (input.getFirstName() == null || input.getFirstName().isBlank()) {
            throw new IllegalArgumentException("First name must not be null or blank");
        }
        if (input.getLastName() == null || input.getLastName().isBlank()) {
            throw new IllegalArgumentException("Last name must not be null or blank");
        }

        // 2. Загрузка должности (если указана)
        Position position = null;
        if (input.getPositionId() != null) {
            position = positionRepository.findById(input.getPositionId())
                    .orElseThrow(() -> new EntityNotFoundException("Position not found with id: " + input.getPositionId()));
        }

        // 4. Создание сотрудника (patronymic, manager, shop, warehouse – не переданы, остаются null)
        Employee employee = Employee.builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .patronymic(null)  // в Input нет patronymic
                .position(position)
                .phone(input.getPhone())
                .email(input.getEmail())
                .manager(null)     // managerId нет в Input
                .shop(null)        // shopId нет в Input
                .warehouse(null)   // warehouseId нет в Input
                .isActive(true)    // по умолчанию активен
                .createdAt(LocalDateTime.now())
                .build();

        return employeeRepository.save(employee);
    }

    @Transactional(readOnly = false)
    public Employee update(Integer id, EmployeeInput input) {
        // 1. Проверка существования
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));

        // 2. Обновление имени (если передано)
        if (input.getFirstName() != null && !input.getFirstName().isBlank()) {
            employee.setFirstName(input.getFirstName());
        } else if (input.getFirstName() != null && input.getFirstName().isBlank()) {
            throw new IllegalArgumentException("First name cannot be blank");
        }

        if (input.getLastName() != null && !input.getLastName().isBlank()) {
            employee.setLastName(input.getLastName());
        } else if (input.getLastName() != null && input.getLastName().isBlank()) {
            throw new IllegalArgumentException("Last name cannot be blank");
        }

        // 3. Обновление email (если передан и изменился)
        if (input.getEmail() != null) {
            if (!input.getEmail().equals(employee.getEmail())) {
                employee.setEmail(input.getEmail().isBlank() ? null : input.getEmail());
            }
        }

        // 4. Обновление телефона
        if (input.getPhone() != null) {
            employee.setPhone(input.getPhone().isBlank() ? null : input.getPhone());
        }

        // 5. Обновление должности (если передан positionId)
        if (input.getPositionId() != null) {
            Position position = positionRepository.findById(input.getPositionId())
                    .orElseThrow(() -> new EntityNotFoundException("Position not found with id: " + input.getPositionId()));
            employee.setPosition(position);
        }

        // Остальные поля (patronymic, manager, shop, warehouse, isActive) не обновляются,
        // т.к. их нет в EmployeeInput. При необходимости их можно добавить в DTO.

        return employeeRepository.save(employee);
    }

    @Transactional(readOnly = false)
    public Boolean deleteById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));

        // Удаление. Если есть зависимые записи (например, сотрудник является менеджером для других),
        // БД выбросит исключение (при условии ON DELETE RESTRICT или аналогичного ограничения).
        employeeRepository.delete(employee);
        return true;
    }
}