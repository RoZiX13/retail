package roz.tan.retail.crud.services;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roz.tan.retail.crud.repositories.CountryRepository;
import roz.tan.retail.models.Country;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CountryService {

    @Autowired
    @Qualifier("countryRepository")
    private CountryRepository countryRepository;

    public Set<Country> findAllByIds(List<Integer> ids){
        return countryRepository.findAllByIds(ids);
    }

}
