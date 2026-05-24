package roz.tan.retail.crud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roz.tan.retail.crud.repositories.CityRepository;
import roz.tan.retail.models.City;

import java.util.List;
import java.util.Set;

@Service
public final class CityService {

    @Autowired
    @Qualifier("cityRepository")
    private CityRepository cityRepository;

    public Set<City> findAllByIds(List<Integer> ids) {
        return cityRepository.findAllByIds(ids);
    }

}
