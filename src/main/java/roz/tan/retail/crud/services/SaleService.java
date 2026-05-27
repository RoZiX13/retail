package roz.tan.retail.crud.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roz.tan.retail.crud.repositories.*;
import roz.tan.retail.models.*;
import roz.tan.retail.utils.dto.SaleInput;
import roz.tan.retail.utils.dto.SaleItemInput;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class SaleService {

    @Autowired
    @Qualifier("saleRepository")
    private SaleRepository saleRepository;

    @Autowired
    @Qualifier("customerRepository")
    private CustomerRepository customerRepository;

    @Autowired
    @Qualifier("shopRepository")
    private ShopRepository shopRepository;

    @Autowired
    @Qualifier("employeeRepository")
    private EmployeeRepository employeeRepository;

    @Autowired
    @Qualifier("productRepository")
    private ProductRepository productRepository;

    @Autowired
    @Qualifier("saleItemRepository")
    private SaleItemRepository saleItemRepository;

    public Set<Sale> findAllByIds(List<Integer> ids) {
        return saleRepository.findAllByIds(ids);
    }

    public Set<Sale> findAllByShopIdAndCustomerId(int shopId, int customerId) {
        return saleRepository
                .findAllByShopIdAndCustomerId(shopId, customerId);
    }

    @Transactional(readOnly = false)
    public Sale save(SaleInput input) {
        // 1. Валидация обязательных полей
        if (input.getCustomerId() == null) {
            throw new IllegalArgumentException("Customer ID must not be null");
        }
        if (input.getShopId() == null) {
            throw new IllegalArgumentException("Shop ID must not be null");
        }
        if (input.getEmployeeId() == null) {
            throw new IllegalArgumentException("Employee ID must not be null");
        }
        if (input.getItems() == null || input.getItems().isEmpty()) {
            throw new IllegalArgumentException("Sale items must not be null or empty");
        }

        // 2. Загрузка и проверка Customer
        Customer customer = customerRepository.findById(input.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + input.getCustomerId()));

        // 3. Загрузка и проверка Shop
        Shop shop = shopRepository.findById(input.getShopId())
                .orElseThrow(() -> new EntityNotFoundException("Shop not found with id: " + input.getShopId()));

        // 4. Загрузка и проверка Employee (исправлено: используется input.getEmployeeId(), а не getShopId)
        Employee employee = employeeRepository.findById(input.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + input.getEmployeeId()));

        // 5. Создание Sale
        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setShop(shop);
        sale.setEmployee(employee);
        // sale.setSaleDatetime(...) – при необходимости; в БД DEFAULT CURRENT_TIMESTAMP

        // 6. Обработка позиций SaleItem
        Set<SaleItem> saleItemSet = new HashSet<>();
        for (SaleItemInput saleItemInput : input.getItems()) {
            if (saleItemInput.getProductId() == null) {
                throw new IllegalArgumentException("Product ID in sale item must not be null");
            }
            if (saleItemInput.getQuantity() == null || saleItemInput.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be positive for product id: " + saleItemInput.getProductId());
            }

            Product product = productRepository.findById(saleItemInput.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + saleItemInput.getProductId()));

            SalePrice salePrice = product.getSalePrice().stream()
                    .max(Comparator.comparing(SalePrice::getCreatedAt))
                    .orElse(null); // предполагается, что у продукта есть актуальная цена продажи
            if (salePrice == null) {
                throw new IllegalStateException("No sale price defined for product id: " + product.getProductId());
            }

            double unitPrice = salePrice.getPriceValue();
            double discountAmount = salePrice.getDiscountAmount();

            // unitPrice и discountAmount на строку передаются в SaleItem,
            // final_price вычисляется БД автоматически (GENERATED ALWAYS)
            saleItemSet.add(SaleItem.builder()
                    .sale(sale) // будет установлено при каскадном сохранении
                    .product(product)
                    .quantity(saleItemInput.getQuantity())
                    .unitPrice(unitPrice)
                    .discountAmount(discountAmount)
                    .build());
        }

        sale.setSaleItem(saleItemSet);

        // 7. Сохранение (каскадно сохранит и SaleItem)
        return saleRepository.save(sale);
    }

    @Transactional(readOnly = false)
    public boolean deleteById(Integer id) {
        saleRepository.deleteById(id);
        return true;
    }

}
