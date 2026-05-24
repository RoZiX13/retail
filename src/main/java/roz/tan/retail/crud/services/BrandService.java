package roz.tan.retail.crud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roz.tan.retail.crud.repositories.BrandRepository;
import roz.tan.retail.crud.repositories.CategoryRepository;
import roz.tan.retail.models.Brand;
import roz.tan.retail.models.Category;

import java.util.List;
import java.util.Set;

@Service
public class BrandService {

    @Autowired
    @Qualifier("brandRepository")
    private BrandRepository brandRepository;

    public Set<Brand> findAllByIds(List<Integer> ids){
        return brandRepository.findAllByIds(ids);
    }

}
