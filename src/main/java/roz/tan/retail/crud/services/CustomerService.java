package roz.tan.retail.crud.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roz.tan.retail.crud.repositories.CityRepository;
import roz.tan.retail.crud.repositories.CustomerRepository;
import roz.tan.retail.models.City;
import roz.tan.retail.models.Customer;
import roz.tan.retail.utils.dto.CustomerInput;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    @Autowired
    @Qualifier("customerRepository")
    private CustomerRepository customerRepository;

    @Autowired
    @Qualifier("cityRepository")
    private CityRepository cityRepository;

    public Set<Customer> findAllByIds(List<Integer> ids) {
        return customerRepository.findAllByIds(ids);
    }

    public Set<Customer> findAllByActive(boolean active) {
        return customerRepository.findAllByIsActive(active);
    }

    @Transactional(readOnly = false)
    public Customer save(CustomerInput input) {
        // 1. Валидация обязательных полей
        if (input.getFirstName() == null || input.getFirstName().isBlank()) {
            throw new IllegalArgumentException("First name must not be null or blank");
        }
        if (input.getLastName() == null || input.getLastName().isBlank()) {
            throw new IllegalArgumentException("Last name must not be null or blank");
        }

        // 2. Загрузка города (если указан)
        City city = null;
        if (input.getCityId() != null) {
            city = cityRepository.findById(input.getCityId())
                    .orElseThrow(() -> new EntityNotFoundException("City not found with id: " + input.getCityId()));
        }

        // 3. Создание клиента
        Customer customer = Customer.builder()
                .firstName(input.getFirstName())
                .patronymic(input.getPatronymic())   // может быть null
                .lastName(input.getLastName())
                .phone(input.getPhone())
                .email(input.getEmail() != null ? input.getEmail().isBlank() ? null : input.getEmail() : null)
                .city(city)
                .address(input.getAddress())
                .isActive(input.getIsActive() != null ? input.getIsActive() : true)
                .createdAt(LocalDateTime.now())
                .build();

        return customerRepository.save(customer);
    }

    @Transactional(readOnly = false)
    public Customer update(Integer id, CustomerInput input) {
        // 1. Проверка существования
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));

        // 2. Обновление имени
        if (input.getFirstName() != null && !input.getFirstName().isBlank()) {
            customer.setFirstName(input.getFirstName());
        } else if (input.getFirstName() != null && input.getFirstName().isBlank()) {
            throw new IllegalArgumentException("First name cannot be blank");
        }

        // 3. Обновление отчества (может быть null)
        if (input.getPatronymic() != null) {
            customer.setPatronymic(input.getPatronymic().isBlank() ? null : input.getPatronymic());
        }

        // 4. Обновление фамилии
        if (input.getLastName() != null && !input.getLastName().isBlank()) {
            customer.setLastName(input.getLastName());
        } else if (input.getLastName() != null && input.getLastName().isBlank()) {
            throw new IllegalArgumentException("Last name cannot be blank");
        }

        // 5. Обновление телефона
        if (input.getPhone() != null) {
            customer.setPhone(input.getPhone().isBlank() ? null : input.getPhone());
        }

        // 6. Обновление города
        if (input.getCityId() != null) {
            City city = cityRepository.findById(input.getCityId())
                    .orElseThrow(() -> new EntityNotFoundException("City not found with id: " + input.getCityId()));
            customer.setCity(city);
        } else if (input.getCityId() == null && customer.getCity() != null) {
            customer.setCity(null);   // сброс города
        }

        // 7. Обновление адреса
        if (input.getAddress() != null) {
            customer.setAddress(input.getAddress().isBlank() ? null : input.getAddress());
        }

        // 8. Обновление активности
        if (input.getIsActive() != null) {
            customer.setActive(input.getIsActive());
        }

        return customerRepository.save(customer);
    }

    @Transactional(readOnly = false)
    public Boolean deleteById(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));

        // Удаление возможно, если нет зависимых продаж (sale)
        // При наличии связей БД выбросит исключение (ON DELETE SET NULL в sale, но если есть другие ограничения)
        customerRepository.delete(customer);
        return true;
    }
}