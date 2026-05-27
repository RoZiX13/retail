package roz.tan.retail.crud.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roz.tan.retail.crud.repositories.UnitRepository;
import roz.tan.retail.models.Unit;
import roz.tan.retail.utils.dto.UnitInput;

import java.util.List;
import java.util.Set;

@Service
public class UnitService {

    @Autowired
    @Qualifier("unitRepository")
    private UnitRepository unitRepository;

    public Set<Unit> findAllByIds(List<Integer> ids) {
        return unitRepository.findAllByIds(ids);
    }

    @Transactional
    public Unit save(UnitInput input) {
        // 1. Валидация полей
        if (input.getName() == null || input.getName().isBlank()) {
            throw new IllegalArgumentException("Unit full name must not be null or blank");
        }
        if (input.getSymbol() == null || input.getSymbol().isBlank()) {
            throw new IllegalArgumentException("Unit short name (symbol) must not be null or blank");
        }

        // 2. Создание и сохранение
        Unit unit = Unit.builder()
                .fullName(input.getName())
                .shortName(input.getSymbol())
                .build();

        return unitRepository.save(unit);
    }

    @Transactional
    public Unit update(Integer id, UnitInput input) {
        // 1. Проверка существования
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unit not found with id: " + id));

        // 2. Обновление full_name (если передано и изменилось)
        if (input.getName() != null && !input.getName().isBlank()) {
            if (!input.getName().equals(unit.getFullName())) {
                unit.setFullName(input.getName());
            }
        } else if (input.getName() != null && input.getName().isBlank()) {
            throw new IllegalArgumentException("Unit full name cannot be blank");
        }

        // 3. Обновление short_name (если передано и изменилось)
        if (input.getSymbol() != null && !input.getSymbol().isBlank()) {
            if (!input.getSymbol().equals(unit.getShortName())) {
                unit.setShortName(input.getSymbol());
            }
        } else if (input.getSymbol() != null && input.getSymbol().isBlank()) {
            throw new IllegalArgumentException("Unit short name cannot be blank");
        }

        return unitRepository.save(unit);
    }

    @Transactional
    public Boolean deleteById(Integer id) {
        // 1. Проверка существования
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unit not found with id: " + id));

        // 2. Удаление (если есть ссылающиеся продукты, БД должна защитить через ON DELETE RESTRICT)
        unitRepository.delete(unit);
        return true;
    }
}