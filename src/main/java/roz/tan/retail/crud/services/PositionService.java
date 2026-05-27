package roz.tan.retail.crud.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roz.tan.retail.crud.repositories.PositionRepository;
import roz.tan.retail.models.Position;
import roz.tan.retail.utils.dto.PositionInput;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class PositionService {

    @Autowired
    @Qualifier("positionRepository")
    private PositionRepository positionRepository;

    public Set<Position> findAllByIds(List<Integer> ids) {
        return positionRepository.findAllByIds(ids);
    }

    @Transactional(readOnly = false)
    public Position save(PositionInput input) {
        // 1. Валидация названия должности
        if (input.getTitle() == null || input.getTitle().isBlank()) {
            throw new IllegalArgumentException("Position title must not be null or blank");
        }

        // 2. Создание и сохранение
        Position position = Position.builder()
                .name(input.getTitle())
                .build();

        return positionRepository.save(position);
    }

    @Transactional(readOnly = false)
    public Position update(Integer id, PositionInput input) {
        // 1. Проверка существования
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Position not found with id: " + id));

        // 2. Обновление названия (если передано и изменилось)
        if (input.getTitle() != null && !input.getTitle().isBlank()) {
            if (!input.getTitle().equals(position.getName())) {
                position.setName(input.getTitle());
            }
        } else if (input.getTitle() != null && input.getTitle().isBlank()) {
            throw new IllegalArgumentException("Position title cannot be blank");
        }

        return positionRepository.save(position);
    }

    @Transactional(readOnly = false)
    public Boolean deleteById(Integer id) {
        // 1. Проверка существования
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Position not found with id: " + id));

        // 2. Удаление (если есть сотрудники с этой должностью, БД выбросит исключение из-за внешнего ключа)
        positionRepository.delete(position);
        return true;
    }
}