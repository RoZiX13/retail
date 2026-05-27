package roz.tan.retail.crud.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roz.tan.retail.crud.repositories.InventoryTransactionRepository;
import roz.tan.retail.crud.repositories.ProductRepository;
import roz.tan.retail.crud.repositories.StockRepository;
import roz.tan.retail.crud.repositories.WarehouseRepository;
import roz.tan.retail.models.InventoryTransaction;
import roz.tan.retail.models.Product;
import roz.tan.retail.models.Stock;
import roz.tan.retail.models.Warehouse;
import roz.tan.retail.utils.dto.StockMovementInput;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class StockService {

    @Autowired
    @Qualifier("stockRepository")
    private StockRepository stockRepository;

    @Autowired
    @Qualifier("productRepository")
    private ProductRepository productRepository;

    @Autowired
    @Qualifier("warehouseRepository")
    private WarehouseRepository warehouseRepository;

    @Autowired
    @Qualifier("inventoryTransactionRepository")
    private InventoryTransactionRepository inventoryTransactionRepository;


    public Set<Stock> getStocksByWarehouseIdAndProductId(
            int warehouseId,
            int productId
    ){
        return stockRepository
                .findByWarehouseIdAndProductId(
                        warehouseId,
                        productId
                );
    }

    @Transactional(readOnly = false)
    public Stock createMovement(StockMovementInput input) {
        // 1. Валидация входных данных
        if (input.getProductId() == null || input.getWarehouseId() == null) {
            throw new IllegalArgumentException("ProductId and WarehouseId must not be null");
        }
        if (input.getQuantity() == null || input.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (input.getType() == null) {
            throw new IllegalArgumentException("ChangeType must be specified");
        }

        // 2. Загружаем Product и Warehouse (для связей в Stock)
        Product product = productRepository.findById(input.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + input.getProductId()));
        Warehouse warehouse = warehouseRepository.findById(input.getWarehouseId())
                .orElseThrow(() -> new EntityNotFoundException("Warehouse not found: " + input.getWarehouseId()));

        // 3. Ищем существующий остаток (уникальная связка продукт+склад)
        Stock stock = stockRepository
                .findByWarehouseIdAndProductId(input.getWarehouseId(), input.getProductId())
                .stream()
                .findFirst()
                .orElseGet(() -> Stock.builder()
                        .product(product)
                        .warehouse(warehouse)
                        .quantity(0.0)
                        .reservedQuantity(0.0)
                        .build());

        double oldQuantity = stock.getQuantity();
        double oldReserved = stock.getReservedQuantity();
        double changeAmount = input.getQuantity();

        // 4. Обработка в зависимости от типа движения
        switch (input.getType()) {
            case IN:
            case RETURN:
                // Поступление товара – увеличиваем quantity
                stock.setQuantity(stock.getQuantity() + changeAmount);
                break;

            case OUT:
                // Отгрузка – уменьшаем quantity, проверяем доступный остаток (quantity - reserved)
                double available = stock.getQuantity() - stock.getReservedQuantity();
                if (available < changeAmount) {
                    throw new IllegalStateException(
                            String.format("Insufficient available stock. Available: %.3f, required: %.3f",
                                    available, changeAmount));
                }
                stock.setQuantity(stock.getQuantity() - changeAmount);
                break;

            case RESERVE:
                // Резервирование – увеличиваем reserved, проверяем что не больше доступного
                double availableForReserve = stock.getQuantity() - stock.getReservedQuantity();
                if (availableForReserve < changeAmount) {
                    throw new IllegalStateException(
                            String.format("Cannot reserve %.3f, only %.3f available", changeAmount, availableForReserve));
                }
                stock.setReservedQuantity(stock.getReservedQuantity() + changeAmount);
                break;

            case RELEASE:
                // Снятие резерва – уменьшаем reserved, проверяем что есть что снять
                if (stock.getReservedQuantity() < changeAmount) {
                    throw new IllegalStateException(
                            String.format("Cannot release %.3f, only %.3f reserved", changeAmount, stock.getReservedQuantity()));
                }
                stock.setReservedQuantity(stock.getReservedQuantity() - changeAmount);
                break;

            case ADJUST:
                // Корректировка остатка – устанавливаем новое абсолютное значение quantity
                if (changeAmount < 0) {
                    throw new IllegalArgumentException("Adjust quantity cannot be negative");
                }
                // При корректировке также проверяем, что новая quantity не меньше зарезервированного
                if (changeAmount < stock.getReservedQuantity()) {
                    throw new IllegalStateException(
                            String.format("Cannot adjust quantity to %.3f because reserved is %.3f",
                                    changeAmount, stock.getReservedQuantity()));
                }
                stock.setQuantity(changeAmount);
                break;

            default:
                throw new UnsupportedOperationException("Unsupported change type: " + input.getType());
        }

        // 5. Сохраняем обновлённый остаток
        Stock savedStock = stockRepository.save(stock);

        InventoryTransaction transaction = InventoryTransaction.builder()
                .product(product)
                .warehouse(warehouse)
                .changeType(input.getType())
                .quantityChange(changeAmount)
                .referenceType(input.getReferenceType())
                .createdAt(LocalDateTime.now())
                .build();
        inventoryTransactionRepository.save(transaction);

        return savedStock;
    }

}
