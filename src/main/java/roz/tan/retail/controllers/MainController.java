package roz.tan.retail.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import roz.tan.retail.crud.services.*;
import roz.tan.retail.models.*;

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
}
