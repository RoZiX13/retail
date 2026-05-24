package roz.tan.retail.crud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import roz.tan.retail.crud.repositories.ProductRepository;
import roz.tan.retail.models.Product;

import java.util.Set;

@Service
public class ProductService {

    @Autowired
    @Qualifier("productRepository")
    private ProductRepository productRepository;

    public Set<Product> findByFilters(String sku,
                                   Integer categoryId,
                                   Integer brandId,
                                   Boolean isActive
    ){
        return productRepository.findByFilters(sku,
                categoryId,
                brandId,
                isActive
        );
    }

}
