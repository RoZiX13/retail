package roz.tan.retail.crud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roz.tan.retail.crud.repositories.CategoryRepository;

import roz.tan.retail.models.Category;

import java.util.List;
import java.util.Set;

@Service
public class CategoryService {

    @Autowired
    @Qualifier("categoryRepository")
    private CategoryRepository categoryRepository;

    public Set<Category> findAllByIds(List<Integer> ids){
        return categoryRepository.findAllByIds(ids);
    }

}
