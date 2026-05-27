package roz.tan.retail.crud.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roz.tan.retail.crud.repositories.CategoryRepository;
import roz.tan.retail.crud.repositories.ProductRepository;
import roz.tan.retail.models.Category;
import roz.tan.retail.utils.dto.CategoryInput;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    @Autowired
    @Qualifier("categoryRepository")
    private CategoryRepository categoryRepository;

    @Autowired
    @Qualifier("productRepository")
    private ProductRepository productRepository;

    @Transactional(readOnly = false)
    public Category save(CategoryInput input) {
        // 1. Валидация имени
        if (input.getName() == null || input.getName().isBlank()) {
            throw new IllegalArgumentException("Category name must not be null or blank");
        }

        // 2. Обработка родительской категории
        Category parent = null;
        if (input.getParentId() != null) {
            parent = categoryRepository.findById(input.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent category not found with id: " + input.getParentId()));
        }

        // 3. Создание и сохранение новой категории
        Category category = Category.builder()
                .name(input.getName())
                .parent(parent)
                .isActive(true)   // по умолчанию активна
                .build();

        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(Integer id, CategoryInput input) {
        // 1. Проверка существования категории
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));

        // 2. Обновление имени (если передано и изменилось)
        if (input.getName() != null && !input.getName().isBlank()) {
            // Проверка уникальности нового имени для текущего родителя
            Integer parentId = (category.getParent() != null) ? category.getParent().getCategoryId() : null;
            category.setName(input.getName());
        }

        // 3. Обновление родителя (если parentId явно передан)
        if (input.getParentId() != null) {
            // Получаем нового родителя
            Category newParent = categoryRepository.findById(input.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent category not found with id: " + input.getParentId()));

            // Проверка на цикл (новый родитель не должен быть потомком текущей категории)
            if (isCycle(category, newParent)) {
                throw new IllegalStateException("Cannot set parent: this would create a cycle in the category hierarchy");
            }

            // Если родитель действительно изменился, проверяем уникальность имени под новым родителем
            if (!newParent.equals(category.getParent())) {
                category.setParent(newParent);
            }
        } else if (input.getParentId() == null && category.getParent() != null) {
            category.setParent(null);
        }

        // Поле isActive не обновляем через CategoryInput, оставляем как есть

        return categoryRepository.save(category);
    }

    @Transactional
    public Boolean deleteById(Integer id) {
        // 1. Проверка существования
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));

        // 2. Удаление (при наличии продуктов ссылка в БД установится в NULL благодаря ON DELETE SET NULL)
        categoryRepository.delete(category);
        return true;
    }

    // Вспомогательный метод для проверки цикла при смене родителя
    private boolean isCycle(Category current, Category potentialParent) {
        Category ancestor = potentialParent;
        while (ancestor != null) {
            if (ancestor.getCategoryId() == current.getCategoryId()) {
                return true;
            }
            ancestor = ancestor.getParent();
        }
        return false;
    }

    public Set<Category> findAllByIds(List<Integer> ids) {
        return categoryRepository.findAllByIds(ids);
    }

}
