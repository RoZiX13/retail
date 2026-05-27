package roz.tan.retail.crud.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roz.tan.retail.crud.repositories.CityRepository;
import roz.tan.retail.crud.repositories.WarehouseRepository;
import roz.tan.retail.models.City;
import roz.tan.retail.models.Warehouse;
import roz.tan.retail.utils.dto.WarehouseInput;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class WarehouseService {

    @Autowired
    @Qualifier("warehouseRepository")
    private WarehouseRepository warehouseRepository;

    @Autowired
    private CityRepository cityRepository;

    public Set<Warehouse> findAllByIds(List<Integer> ids) {
        return warehouseRepository.findAllByIds(ids);
    }

    @Transactional(readOnly = false)
    public Warehouse save(WarehouseInput input) {
        // 1. Валидация обязательных полей
        if (input.getName() == null || input.getName().isBlank()) {
            throw new IllegalArgumentException("Warehouse name must not be null or blank");
        }
        if (input.getAddress() == null || input.getAddress().isBlank()) {
            throw new IllegalArgumentException("Warehouse address must not be null or blank");
        }

        // 2. Загрузка города (если указан)
        City city = null;
        if (input.getCityId() != null) {
            city = cityRepository.findById(input.getCityId())
                    .orElseThrow(() -> new EntityNotFoundException("City not found with id: " + input.getCityId()));
        }

        // 3. Создание и сохранение склада
        Warehouse warehouse = Warehouse.builder()
                .name(input.getName())
                .address(input.getAddress())
                .city(city)
                .isActive(input.getIsActive() != null ? input.getIsActive() : true) // по умолчанию активен
                .build();

        return warehouseRepository.save(warehouse);
    }

    @Transactional(readOnly = false)
    public Warehouse update(Integer id, WarehouseInput input) {
        // 1. Проверка существования склада
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Warehouse not found with id: " + id));

        // 2. Обновление имени (если передано и изменилось)
        if (input.getName() != null && !input.getName().isBlank()) {
            if (!input.getName().equals(warehouse.getName())) {
                warehouse.setName(input.getName());
            }
        } else if (input.getName() != null && input.getName().isBlank()) {
            throw new IllegalArgumentException("Warehouse name cannot be blank");
        }

        // 3. Обновление адреса (если передан и изменился)
        if (input.getAddress() != null && !input.getAddress().isBlank()) {
            if (!input.getAddress().equals(warehouse.getAddress())) {
                warehouse.setAddress(input.getAddress());
            }
        } else if (input.getAddress() != null && input.getAddress().isBlank()) {
            throw new IllegalArgumentException("Warehouse address cannot be blank");
        }

        // 4. Обновление города
        if (input.getCityId() != null) {
            City city = cityRepository.findById(input.getCityId())
                    .orElseThrow(() -> new EntityNotFoundException("City not found with id: " + input.getCityId()));
            warehouse.setCity(city);
        } else if (input.getCityId() == null && warehouse.getCity() != null) {
            // Явное указание null для сброса города
            warehouse.setCity(null);
        }

        // 5. Обновление активности (если передано)
        if (input.getIsActive() != null) {
            warehouse.setActive(input.getIsActive());
        }

        return warehouseRepository.save(warehouse);
    }

    @Transactional(readOnly = false)
    public Boolean deleteById(Integer id) {
        // 1. Проверка существования
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Warehouse not found with id: " + id));

        // 2. Проверка, не используется ли склад (например, есть остатки stock или сотрудники)
        // Поскольку в БД есть внешние ключи с ограничениями, при попытке удалить склад,
        // на который ссылается stock или employee, будет выброшено исключение от СУБД.
        // Можем добавить предварительную проверку при необходимости.

        warehouseRepository.delete(warehouse);
        return true;
    }
}