package roz.tan.retail.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import roz.tan.retail.crud.services.CountryService;
import roz.tan.retail.crud.services.CityService;
import roz.tan.retail.crud.services.CategoryService;
import roz.tan.retail.crud.services.UnitService;
import roz.tan.retail.crud.services.BrandService;
import roz.tan.retail.crud.services.ProductService;
import roz.tan.retail.crud.services.WarehouseService;
import roz.tan.retail.crud.services.SaleService;
import roz.tan.retail.crud.services.StockService;
import roz.tan.retail.crud.services.EmployeeService;
import roz.tan.retail.crud.services.PositionService;
import roz.tan.retail.crud.services.SupplierService;
import roz.tan.retail.crud.services.CustomerService;

import roz.tan.retail.models.Country;
import roz.tan.retail.models.City;
import roz.tan.retail.models.Category;
import roz.tan.retail.models.Unit;
import roz.tan.retail.models.Brand;
import roz.tan.retail.models.Product;
import roz.tan.retail.models.Warehouse;
import roz.tan.retail.models.Sale;
import roz.tan.retail.models.Stock;
import roz.tan.retail.models.Employee;
import roz.tan.retail.models.Position;
import roz.tan.retail.models.Supplier;
import roz.tan.retail.models.Customer;
import roz.tan.retail.utils.dto.*;

import java.util.List;
import java.util.Set;

@Controller
public final class MainController {

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

    @Autowired
    @Qualifier("warehouseService")
    private WarehouseService warehouseService;

    @Autowired
    @Qualifier("saleService")
    private SaleService saleService;

    @Autowired
    @Qualifier("stockService")
    private StockService stockService;

    @Autowired
    @Qualifier("employeeService")
    private EmployeeService employeeService;

    @Autowired
    @Qualifier("positionService")
    private PositionService positionService;

    @Autowired
    @Qualifier("supplierService")
    private SupplierService supplierService;

    @Autowired
    @Qualifier("customerService")
    private CustomerService customerService;

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

    @QueryMapping("products")
    public Set<Product> getProducts(@Argument List<Integer> ids) {
        return productService.findAllByIds(ids);
    }

    @QueryMapping("warehouses")
    public Set<Warehouse> getWarehouse(@Argument List<Integer> ids) {
        return warehouseService.findAllByIds(ids);
    }

    @QueryMapping("sales")
    public Set<Sale> getSales(@Argument List<Integer> ids) {
        return saleService.findAllByIds(ids);
    }

    @QueryMapping("employees")
    public Set<Employee> getEmployees(@Argument List<Integer> ids) {
        return employeeService.findAllByIds(ids);
    }

    @QueryMapping("positions")
    public Set<Position> getPositions(@Argument List<Integer> ids) {
        return positionService.findAllByIds(ids);
    }

    @QueryMapping("suppliers")
    public Set<Supplier> getSuppliers(@Argument List<Integer> ids) {
        return supplierService.findAllByIds(ids);
    }

    @QueryMapping("customers")
    public Set<Customer> getCustomers(@Argument List<Integer> ids) {
        return customerService.findAllByIds(ids);
    }


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

    @QueryMapping("sales_by_shop_id_and_customer_id")
    public Set<Sale> getSalesByShopIdAndCustomerId(
            @Argument Integer shopId,
            @Argument Integer customerId
            ) {
        return saleService.findAllByShopIdAndCustomerId(shopId, customerId);
    }

    @QueryMapping("stocks")
    public Set<Stock> getStocks(
            @Argument Integer warehouseId,
            @Argument Integer productId
    ){
        return stockService
                .getStocksByWarehouseIdAndProductId(
                        warehouseId,
                        productId
                );
    }

    @QueryMapping("suppliers_by_active")
    public Set<Supplier> getSuppliersByActive(@Argument Boolean active) {
        return supplierService.findAllByActive(active);
    }

    @QueryMapping("customers_by_active")
    public Set<Customer> getCustomersByActive(@Argument Boolean active) {
        return customerService.findAllByActive(active);
    }

    // ==============================
    // MutationMapping методы
    // ==============================

    // ---------- Продукты ----------
    @MutationMapping("createProduct")
    public Product createProduct(@Argument ProductInput input) {
        return productService.save(input);
    }

    @MutationMapping("updateProduct")
    public Product updateProduct(@Argument Integer id, @Argument ProductInput input) {
        return productService.update(id, input);
    }

    @MutationMapping("deleteProduct")
    public Boolean deleteProduct(@Argument Integer id) {
        return productService.deleteById(id);
    }

    // ---------- Продажи ----------
    @MutationMapping("createSale")
    public Sale createSale(@Argument SaleInput input) {
        return saleService.save(input);
    }

    @MutationMapping("deleteSale")
    public Boolean deleteSale(@Argument Integer id) {
        return saleService.deleteById(id);
    }

    // ---------- Движение товаров ----------
    @MutationMapping("createStockMovement")
    public Stock createStockMovement(@Argument StockMovementInput input) {
        return stockService.createMovement(input);
    }

    // ---------- Категории ----------
    @MutationMapping("createCategory")
    public Category createCategory(@Argument CategoryInput input) {
        return categoryService.save(input);
    }

    @MutationMapping("updateCategory")
    public Category updateCategory(@Argument Integer id, @Argument CategoryInput input) {
        return categoryService.update(id, input);
    }

    @MutationMapping("deleteCategory")
    public Boolean deleteCategory(@Argument Integer id) {
        return categoryService.deleteById(id);
    }

    // ---------- Бренды ----------
    @MutationMapping("createBrand")
    public Brand createBrand(@Argument BrandInput input) {
        return brandService.save(input);
    }

    @MutationMapping("updateBrand")
    public Brand updateBrand(@Argument Integer id, @Argument BrandInput input) {
        return brandService.update(id, input);
    }

    @MutationMapping("deleteBrand")
    public Boolean deleteBrand(@Argument Integer id) {
        return brandService.deleteById(id);
    }

    // ---------- Единицы измерения ----------
    @MutationMapping("createUnit")
    public Unit createUnit(@Argument UnitInput input) {
        return unitService.save(input);
    }

    @MutationMapping("updateUnit")
    public Unit updateUnit(@Argument Integer id, @Argument UnitInput input) {
        return unitService.update(id, input);
    }

    @MutationMapping("deleteUnit")
    public Boolean deleteUnit(@Argument Integer id) {
        return unitService.deleteById(id);
    }

    // ---------- Склады ----------
    @MutationMapping("createWarehouse")
    public Warehouse createWarehouse(@Argument WarehouseInput input) {
        return warehouseService.save(input);
    }

    @MutationMapping("updateWarehouse")
    public Warehouse updateWarehouse(@Argument Integer id, @Argument WarehouseInput input) {
        return warehouseService.update(id, input);
    }

    @MutationMapping("deleteWarehouse")
    public Boolean deleteWarehouse(@Argument Integer id) {
        return warehouseService.deleteById(id);
    }

    // ---------- Сотрудники ----------
    @MutationMapping("createEmployee")
    public Employee createEmployee(@Argument EmployeeInput input) {
        return employeeService.save(input);
    }

    @MutationMapping("updateEmployee")
    public Employee updateEmployee(@Argument Integer id, @Argument EmployeeInput input) {
        return employeeService.update(id, input);
    }

    @MutationMapping("deleteEmployee")
    public Boolean deleteEmployee(@Argument Integer id) {
        return employeeService.deleteById(id);
    }

    // ---------- Должности ----------
    @MutationMapping("createPosition")
    public Position createPosition(@Argument PositionInput input) {
        return positionService.save(input);
    }

    @MutationMapping("updatePosition")
    public Position updatePosition(@Argument Integer id, @Argument PositionInput input) {
        return positionService.update(id, input);
    }

    @MutationMapping("deletePosition")
    public Boolean deletePosition(@Argument Integer id) {
        return positionService.deleteById(id);
    }

    // ---------- Поставщики ----------
    @MutationMapping("createSupplier")
    public Supplier createSupplier(@Argument SupplierInput input) {
        return supplierService.save(input);
    }

    @MutationMapping("updateSupplier")
    public Supplier updateSupplier(@Argument Integer id, @Argument SupplierInput input) {
        return supplierService.update(id, input);
    }

    @MutationMapping("deleteSupplier")
    public Boolean deleteSupplier(@Argument Integer id) {
        return supplierService.deleteById(id);
    }

    // ---------- Клиенты ----------
    @MutationMapping("createCustomer")
    public Customer createCustomer(@Argument CustomerInput input) {
        return customerService.save(input);
    }

    @MutationMapping("updateCustomer")
    public Customer updateCustomer(@Argument Integer id, @Argument CustomerInput input) {
        return customerService.update(id, input);
    }

    @MutationMapping("deleteCustomer")
    public Boolean deleteCustomer(@Argument Integer id) {
        return customerService.deleteById(id);
    }

    // ---------- Города ----------
    @MutationMapping("createCity")
    public City createCity(@Argument CityInput input) {
        return cityService.save(input);
    }

    @MutationMapping("updateCity")
    public City updateCity(@Argument Integer id, @Argument CityInput input) {
        return cityService.update(id, input);
    }

    @MutationMapping("deleteCity")
    public Boolean deleteCity(@Argument Integer id) {
        return cityService.deleteById(id);
    }

    // ---------- Страны ----------
    @MutationMapping("createCountry")
    public Country createCountry(@Argument CountryInput input) {
        return countryService.save(input);
    }

    @MutationMapping("updateCountry")
    public Country updateCountry(@Argument Integer id, @Argument CountryInput input) {
        return countryService.update(id, input);
    }

    @MutationMapping("deleteCountry")
    public Boolean deleteCountry(@Argument Integer id) {
        return countryService.deleteById(id);
    }

}
