package roz.tan.retail.crud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roz.tan.retail.crud.repositories.UnitRepository;
import roz.tan.retail.models.Unit;

import java.util.List;
import java.util.Set;

@Service
public class UnitService {

    @Autowired
    @Qualifier("unitRepository")
    private UnitRepository unitRepository;

    public Set<Unit> findAllByIds(List<Integer> ids) {
        return unitRepository.findAllByIds(ids);
    }

}
