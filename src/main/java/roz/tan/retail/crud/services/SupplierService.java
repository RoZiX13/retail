package roz.tan.retail.crud.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roz.tan.retail.crud.repositories.CityRepository;
import roz.tan.retail.crud.repositories.SupplierRepository;
import roz.tan.retail.models.City;
import roz.tan.retail.models.Supplier;
import roz.tan.retail.utils.dto.SupplierInput;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true)
public class SupplierService {

    private static final Pattern INN_PATTERN_10 = Pattern.compile("^\\d{10}$");
    private static final Pattern INN_PATTERN_12 = Pattern.compile("^\\d{12}$");
    private static final Pattern KPP_PATTERN = Pattern.compile("^\\d{9}$");

    @Autowired
    @Qualifier("supplierRepository")
    private SupplierRepository supplierRepository;

    @Autowired
    @Qualifier("cityRepository")
    private CityRepository cityRepository;

    public Set<Supplier> findAllByIds(List<Integer> ids) {
        return supplierRepository.findAllByIds(ids);
    }

    @Transactional(readOnly = false)
    public Supplier save(SupplierInput input) {
        // 1. Валидация обязательного поля name
        if (input.getName() == null
                || input.getName().isBlank()) {
            throw new IllegalArgumentException(
                    "Supplier name must not be null or blank"
            );
        }

        // 2. Валидация ИНН (если указан)
        if (input.getInn() != null
                && !input.getInn().isBlank()
        ) {
            if (!INN_PATTERN_10
                    .matcher(input.getInn()).matches()
                    && !INN_PATTERN_12
                        .matcher(input.getInn()).matches()
            ) {
                throw new IllegalArgumentException(
                        "INN must be 10 or 12 digits"
                );
            }
        }

        // 3. Валидация КПП (если указан)
        if (input.getKpp() != null
                && !input.getKpp().isBlank()
        ) {
            if (!KPP_PATTERN
                    .matcher(
                            input.getKpp())
                    .matches()
            ) {
                throw new IllegalArgumentException(
                        "KPP must be 9 digits"
                );
            }
        }

        // 4. Загрузка города (если указан)
        City city = null;
        if (input.getCityId() != null) {
            city = cityRepository
                    .findById(input.getCityId())
                    .orElseThrow(() ->
                            new EntityNotFoundException(
                                    "City not found with id: "
                                            + input.getCityId()
                            )
                    );
        }

        // 5. Создание поставщика
        Supplier supplier =
                Supplier.builder()
                .name(input.getName())
                .inn(input.getInn())
                .kpp(input.getKpp())
                .contactPerson(
                        input.getContactPerson()
                )
                .phone(input.getPhone())
                .email(input.getEmail())
                .city(city)
                .address(input.getAddress())
                .isActive(
                        input.getIsActive() != null
                                ? input.getIsActive()
                                : true
                )
                .createdAt(LocalDateTime.now())
                .build();

        return supplierRepository
                .save(supplier);
    }

    @Transactional(readOnly = false)
    public Supplier update(
            Integer id,
            SupplierInput input
    ) {
        // 1. Проверка существования
        Supplier supplier = supplierRepository
                .findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Supplier not found with id: "
                                        + id
                        )
                );

        // 2. Обновление name (если передан)
        if (input.getName() != null
                && !input.getName().isBlank()
        ) {
            supplier.setName(
                    input.getName()
            );
        } else if (input.getName() != null
                && input.getName().isBlank()
        ) {
            throw new IllegalArgumentException(
                    "Supplier name cannot be blank"
            );
        }

        // 3. Обновление ИНН (если передан)
        if (input.getInn() != null) {
            String inn = input.getInn().isBlank()
                    ? null
                    : input.getInn();
            if (inn != null
                    && !INN_PATTERN_10
                        .matcher(inn).matches()
                    && !INN_PATTERN_12
                        .matcher(inn).matches()) {
                throw new IllegalArgumentException(
                        "INN must be 10 or 12 digits"
                );
            }
            supplier.setInn(inn);
        }

        // 4. Обновление КПП
        if (input.getKpp() != null) {
            String kpp = input.getKpp().isBlank()
                    ? null
                    : input.getKpp();
            if (kpp != null
                    && !KPP_PATTERN.matcher(kpp).matches()) {
                throw new IllegalArgumentException(
                        "KPP must be 9 digits"
                );
            }
            supplier.setKpp(kpp);
        }

        // 5. Обновление контактного лица
        if (input.getContactPerson() != null) {
            supplier.setContactPerson(
                    input.getContactPerson().isBlank()
                            ? null
                            : input.getContactPerson()
            );
        }

        // 6. Обновление телефона
        if (input.getPhone() != null) {
            supplier.setPhone(
                    input.getPhone().isBlank()
                            ? null
                            : input.getPhone()
            );
        }

        // 7. Обновление email
        if (input.getEmail() != null) {
            supplier.setEmail(
                    input.getEmail().isBlank()
                            ? null
                            : input.getEmail()
            );
        }

        // 8. Обновление города
        if (input.getCityId() != null) {
            City city = cityRepository
                    .findById(input.getCityId())
                    .orElseThrow(() ->
                            new EntityNotFoundException(
                                    "City not found with id: "
                                            + input.getCityId()
                            )
                    );
            supplier.setCity(city);
        } else if (input.getCityId() == null
                && supplier.getCity() != null
        ) {
            supplier.setCity(null); // сброс города
        }

        // 9. Обновление адреса
        if (input.getAddress() != null) {
            supplier.setAddress(
                    input.getAddress().isBlank()
                            ? null
                            : input.getAddress()
            );
        }

        // 10. Обновление статуса активности
        if (input.getIsActive() != null) {
            supplier.setActive(
                    input.getIsActive()
            );
        }

        return supplierRepository
                .save(supplier);
    }

    @Transactional(readOnly = false)
    public Boolean deleteById(Integer id) {
        Supplier supplier = supplierRepository
                .findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Supplier not found with id: "
                                        + id
                        )
                );
        supplierRepository
                .delete(supplier);
        return true;
    }

    public Set<Supplier> findAllByActive(boolean active) {
        return supplierRepository
                .findAllByIsActive(active);
    }

}
