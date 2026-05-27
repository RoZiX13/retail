package roz.tan.retail.crud.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roz.tan.retail.crud.repositories.BrandRepository;
import roz.tan.retail.crud.repositories.CategoryRepository;
import roz.tan.retail.crud.repositories.ProductRepository;
import roz.tan.retail.crud.repositories.UnitRepository;
import roz.tan.retail.models.Brand;
import roz.tan.retail.models.Product;
import roz.tan.retail.models.Unit;
import roz.tan.retail.models.Category;
import roz.tan.retail.models.PurchasePrice;
import roz.tan.retail.models.SalePrice;
import roz.tan.retail.utils.dto.ProductInput;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ProductService {

    @Autowired
    @Qualifier("productRepository")
    private ProductRepository productRepository;

    @Autowired
    @Qualifier("categoryRepository")
    private CategoryRepository categoryRepository;

    @Autowired
    @Qualifier("brandRepository")
    private BrandRepository brandRepository;

    @Autowired
    @Qualifier("unitRepository")
    private UnitRepository unitRepository;

    public Set<Product> findByFilters(String sku,
                                      Integer categoryId,
                                      Integer brandId,
                                      Boolean isActive
    ) {
        return productRepository.findByFilters(sku,
                categoryId,
                brandId,
                isActive
        );
    }

    public Set<Product> findAllByIds(List<Integer> ids) {
        return productRepository
                .findAllByIds(ids);
    }

    @Transactional(readOnly = false)
    public Product save(ProductInput input) {
        // 1. Валидация обязательных полей
        if (input.getSku() == null
                || input.getSku().isBlank()
        ) {
            throw new IllegalArgumentException(
                    "SKU must not be null or blank"
            );
        }
        if (input.getName() == null
                || input.getName().isBlank()
        ) {
            throw new IllegalArgumentException(
                    "Product name must not be null or blank"
            );
        }
        if (input.getUnitId() == null) {
            throw new IllegalArgumentException(
                    "Unit ID must not be null"
            );
        }
        if (input.getPurchasePrice() == null
                || input.getPurchasePrice() <= 0
        ) {
            throw new IllegalArgumentException(
                    "Purchase price must be positive"
            );
        }
        if (input.getSalePrice() == null
                || input.getSalePrice() <= 0
        ) {
            throw new IllegalArgumentException(
                    "Sale price must be positive"
            );
        }
        if (
                input.getSalePrice() < input.getPurchasePrice()
        ) {
            throw new IllegalArgumentException(
                    "Sale price cannot be less than purchase price"
            );
        }

        // 2. Создание и заполнение Product
        Product product = new Product();
        product.setSku(input.getSku());
        product.setName(input.getName());
        product.setDescription(input.getDescription()); // может быть null
        product.setActive(
                input.getIsActive() == null
                        ? true
                        : input.getIsActive()
        );

        // 3. Загрузка и установка Category (опционально)
        if (input.getCategoryId() != null) {
            Category category = categoryRepository
                    .findById(input.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Category not found with id: "
                                    + input.getCategoryId()
                            )
                    );
            product.setCategory(category);
        }

        // 4. Загрузка и установка Brand (опционально)
        if (input.getBrandId() != null) {
            Brand brand = brandRepository
                    .findById(input.getBrandId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Brand not found with id: "
                                    + input.getBrandId()
                            )
                    );
            product.setBrand(brand);
        }

        // 5. Загрузка и установка Unit (обязательно)
        Unit unit = unitRepository
                .findById(input.getUnitId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                        "Unit not found with id: "
                                + input.getUnitId()
                        )
                );
        product.setUnit(unit);

        // 6. Создание PurchasePrice и SalePrice
        PurchasePrice purchasePrice =
                PurchasePrice.builder()
                .product(product)
                .priceValue(input.getPurchasePrice())
                .createdAt(LocalDateTime.now())
                .build();

        SalePrice salePrice
                = SalePrice.builder()
                .product(product)
                .priceValue(input.getSalePrice())   // исправлено: salePrice, а не purchasePrice
                .discountAmount(0.0)                // можно задать из input, если есть поле
                .createdAt(LocalDateTime.now())
                .build();

        product.getPurchasePrice()
                .add(purchasePrice);
        product.getSalePrice()
                .add(salePrice);

        // 7. Сохранение
        return productRepository
                .saveAndFlush(product);
    }

    @Transactional(readOnly = false)
    public Product update(Integer id, ProductInput input) {
        // 1. Проверка существования продукта
        if (id == null) {
            throw new IllegalArgumentException(
                    "Product ID must not be null"
            );
        }
        Product product = productRepository
                .findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Product not found with id: "
                                        + id
                        )
                );

        // 2. Валидация обязательных полей (если они переданы, то они должны быть корректны)
        if (input.getSku() != null
                && input.getSku().isBlank()
        ) {
            throw new IllegalArgumentException(
                    "SKU cannot be blank"
            );
        }
        if (input.getName() != null
                && input.getName().isBlank()
        ) {
            throw new IllegalArgumentException(
                    "Product name cannot be blank"
            );
        }
        if (input.getUnitId() != null) {
            // Проверим, что unit существует, но установим позже
            unitRepository.findById(
                    input.getUnitId()
                    )
                    .orElseThrow(() ->
                            new EntityNotFoundException(
                                    "Unit not found with id: "
                                            + input.getUnitId()
                            )
                    );
        }
        if (input.getPurchasePrice() != null
                && input.getPurchasePrice() <= 0
        ) {
            throw new IllegalArgumentException(
                    "Purchase price must be positive"
            );
        }
        if (input.getSalePrice() != null
                && input.getSalePrice() <= 0) {
            throw new IllegalArgumentException(
                    "Sale price must be positive"
            );
        }
        if (input.getPurchasePrice() != null
                && input.getSalePrice() != null
                && input.getSalePrice() < input.getPurchasePrice()
        ) {
            throw new IllegalArgumentException(
                    "Sale price cannot be less than purchase price"
            );
        }

        // 3. Обновление простых полей (только если они переданы)
        if (input.getSku() != null) {
            product.setSku(input.getSku());
        }
        if (input.getName() != null) {
            product.setName(input.getName());
        }
        if (input.getDescription() != null) {
            product.setDescription(input.getDescription());
        }
        if (input.getIsActive() != null) {
            product.setActive(input.getIsActive());
        }

        // 4. Обновление связей (Category, Brand, Unit)
        if (input.getCategoryId() != null) {
            Category category = categoryRepository
                    .findById(input.getCategoryId()
                    )
                    .orElseThrow(() ->
                            new EntityNotFoundException(
                                    "Category not found with id: "
                                            + input.getCategoryId()
                            )
                    );
            product.setCategory(category);
        } else if (input.getCategoryId() == null) {
            // Если нужно явно удалить категорию – используйте дополнительный флаг
            product.setCategory(null);
        }

        if (input.getBrandId() != null) {
            Brand brand = brandRepository
                    .findById(input.getBrandId())
                    .orElseThrow(() ->
                            new EntityNotFoundException(
                                    "Brand not found with id: "
                                            + input.getBrandId()
                            )
                    );
            product.setBrand(brand);
        } else if (input.getBrandId() == null) {
            product.setBrand(null);
        }

        if (input.getUnitId() != null) {
            Unit unit = unitRepository
                    .findById(input.getUnitId())
                    .orElseThrow(() ->
                            new EntityNotFoundException(
                                    "Unit not found with id: "
                                            + input.getUnitId()
                            )
                    );
            product.setUnit(unit);
        }

        // 5. Обновление цен (создаются новые записи с текущим временем)
        if (input.getPurchasePrice() != null) {
            PurchasePrice purchasePrice =
                    PurchasePrice.builder()
                    .product(product)
                    .priceValue(input.getPurchasePrice())
                    .createdAt(LocalDateTime.now())
                    .build();
            product.getPurchasePrice().add(purchasePrice);
        }

        if (input.getSalePrice() != null) {
            SalePrice salePrice =
                    SalePrice.builder()
                    .product(product)
                    .priceValue(input.getSalePrice())
                    .discountAmount(0.0) // при необходимости берите из input
                    .createdAt(LocalDateTime.now())
                    .build();
            product
                    .getSalePrice()
                    .add(salePrice);
        }

        // 6. Сохранение
        return productRepository
                .saveAndFlush(product);
    }

    public Boolean deleteById(Integer id) {
        productRepository
                .deleteById(id);
        return true;
    }

}
