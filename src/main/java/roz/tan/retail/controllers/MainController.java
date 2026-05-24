package roz.tan.retail.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roz.tan.retail.crud.services.*;
import roz.tan.retail.models.*;

import java.util.List;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    @Qualifier("countryService")
    private CountryService countryService;

    @Autowired
    @Qualifier("cityService")
    private CityService cityService;

    @Autowired
    @Qualifier("categoryService")
    private CategoryService categoryService;

    @Autowired
    @Qualifier("unitService")
    private UnitService unitService;

    @Autowired
    @Qualifier("brandService")
    private BrandService brandService;

    @Autowired
    @Qualifier("productService")
    private ProductService productService;

    @QueryMapping("countries")
    public Set<Country> getCountries(@Argument List<Integer> ids) {
        return countryService.findAllByIds(ids);
    }

    @QueryMapping("cities")
    public Set<City> getCities(@Argument List<Integer> ids) {
        return cityService.findAllByIds(ids);
    }

    @QueryMapping("categories")
    public Set<Category> getCategories(@Argument List<Integer> ids) {
        return categoryService.findAllByIds(ids);
    }

    @QueryMapping("units")
    public Set<Unit> getUnits(@Argument List<Integer> ids) {
        return unitService.findAllByIds(ids);
    }

    @QueryMapping("brands")
    public Set<Brand> getBrands(@Argument List<Integer> ids) {
        return brandService.findAllByIds(ids);
    }

    // Товары
    @QueryMapping("products_by_filter")
    public Set<Product> getProducts(
            @Argument String sku,
            @Argument Integer categoryId,
            @Argument Integer brandId,
            @Argument Boolean isActive) {
        return productService.findByFilters(
                sku,
                categoryId,
                brandId,
                isActive);
    }
//
//    // Склад
//    @GetMapping("/stocks")
//    public Set<StockDto> getStocks(
//            @RequestParam(required = false) String warehouseId,
//            @RequestParam(required = false) String productId) {
//        return List.of(new StockDto("1", null, null, 100.0, 5.0, LocalDateTime.now().toString()));
//    }
//
//    @GetMapping("/warehouses")
//    public Set<WarehouseDto> getWarehouses() {
//        return List.of(new WarehouseDto("1", "Main Warehouse", "Address", null, true, null, null));
//    }
//
//    // Продажи
//    @GetMapping("/sales")
//    public Set<SaleDto> getSales(
//            @RequestParam(required = false) String shopId,
//            @RequestParam(required = false) String customerId,
//            @RequestParam(required = false) String status) {
//        return List.of(new SaleDto("1", null, null, null, LocalDateTime.now().toString(), "COMPLETED", null));
//    }
//
//    @GetMapping("/sales/{id}")
//    public SaleDto getSale(@PathVariable String id) {
//        return new SaleDto(id, null, null, null, LocalDateTime.now().toString(), "COMPLETED", null);
//    }
//
//    // Персонал
//    @GetMapping("/employees")
//    public Set<EmployeeDto> getEmployees(
//            @RequestParam(required = false) String shopId,
//            @RequestParam(required = false) String warehouseId) {
//        return List.of(new EmployeeDto("1", "Ivan", "Ivanovich", "Ivanov", null, "+70000000000", "ivan@example.com", null, null, null, true));
//    }
//
//    @GetMapping("/positions")
//    public Set<PositionDto> getPositions() {
//        return List.of(new PositionDto("1", "Manager", null));
//    }
//
//    // Поставщики / Клиенты
//    @GetMapping("/suppliers")
//    public Set<SupplierDto> getSuppliers(@RequestParam(required = false) Boolean isActive) {
//        return List.of(new SupplierDto("1", "Supplier Ltd", "1234567890", "123456789", "John Doe", "+71111111111", "supplier@example.com", null, "Address", true));
//    }
//
//    @GetMapping("/customers")
//    public Set<CustomerDto> getCustomers(@RequestParam(required = false) Boolean isActive) {
//        return List.of(new CustomerDto("1", "Petr", "Petrovich", "Petrov", "+72222222222", "petr@example.com", null, "Address", true, null));
//    }
//
//    // ============================
//    //          MUTATIONS
//    // ============================
//
//    @PostMapping("/products")
//    public ProductDto createProduct(@RequestBody ProductInputDto input) {
//        // stub: generate new id
//        return new ProductDto(UUID.randomUUID().toString(), input.getSku(), input.getName(), input.getDescription(),
//                null, null, null, input.getIsActive(), LocalDateTime.now().toString(), null, null);
//    }
//
//    @PutMapping("/products/{id}")
//    public ProductDto updateProduct(@PathVariable String id, @RequestBody ProductInputDto input) {
//        return new ProductDto(id, input.getSku(), input.getName(), input.getDescription(),
//                null, null, null, input.getIsActive(), LocalDateTime.now().toString(), null, null);
//    }
//
//    @DeleteMapping("/products/{id}")
//    public boolean deleteProduct(@PathVariable String id) {
//        return true;
//    }
//
//    @PostMapping("/sales")
//    public SaleDto createSale(@RequestBody SaleInputDto input) {
//        // stub
//        return new SaleDto(UUID.randomUUID().toString(), null,
//                new ShopDto(input.getShopId(), null, null, null, true, null, null),
//                null, LocalDateTime.now().toString(), "PENDING", null);
//    }
//
//    @PatchMapping("/sales/{id}/status")
//    public SaleDto updateSaleStatus(@PathVariable String id, @RequestParam String status) {
//        return new SaleDto(id, null, null, null, LocalDateTime.now().toString(), status, null);
//    }
//
//    @DeleteMapping("/sales/{id}")
//    public boolean cancelSale(@PathVariable String id) {
//        return true;
//    }
//
//    @PostMapping("/stock-movements")
//    public StockDto createStockMovement(@RequestBody StockMovementInputDto input) {
//        return new StockDto(UUID.randomUUID().toString(), null, null, input.getQuantityChange(), 0.0, LocalDateTime.now().toString());
//    }
}