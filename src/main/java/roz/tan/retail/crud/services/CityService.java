package roz.tan.retail.crud.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roz.tan.retail.crud.repositories.CityRepository;
import roz.tan.retail.crud.repositories.CountryRepository;
import roz.tan.retail.models.City;
import roz.tan.retail.models.Country;
import roz.tan.retail.utils.dto.CityInput;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class CityService {

    @Autowired
    @Qualifier("cityRepository")
    private CityRepository cityRepository;

    @Autowired
    @Qualifier("countryRepository")
    private CountryRepository countryRepository;

    public Set<City> findAllByIds(List<Integer> ids) {
        return cityRepository.findAllByIds(ids);
    }

    @Transactional(readOnly = false)
    public City save(CityInput input) {
        // 1. Валидация имени
        if (input.getName() == null
                || input.getName().isBlank()
        ) {
            throw new IllegalArgumentException(
                    "City name must not be null or blank"
            );
        }
        // 2. Валидация страны
        if (input.getCountryId() == null) {
            throw new IllegalArgumentException(
                    "Country ID must not be null"
            );
        }
        Country country = countryRepository.findById(input.getCountryId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Country not found with id: "
                                        + input.getCountryId()
                        )
                );

        City city = City.builder()
                .name(
                        input.getName()
                )
                .country(country)
                .build();

        return cityRepository
                .save(city);
    }

    @Transactional(readOnly = false)
    public City update(Integer id, CityInput input) {
        // 1. Проверка существования
        City city = cityRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "City not found with id: "
                                        + id)
                );

        // 2. Обновление имени
        if (input.getName() != null && !input.getName().isBlank()) {
            // Если имя меняется, проверить уникальность с текущей страной
            if (!input.getName()
                    .equals(city.getName())
            ) {
                city.setName(
                        input.getName()
                );
            }
        } else if (
                input.getName() != null
                && input.getName().isBlank()
        ) {
            throw new IllegalArgumentException(
                    "City name cannot be blank"
            );
        }

        // 3. Обновление страны (если передан countryId и он отличается)
        if (input.getCountryId() != null) {
            Country newCountry = countryRepository
                    .findById(input.getCountryId())
                    .orElseThrow(() ->
                            new EntityNotFoundException(
                                    "Country not found with id: "
                                            + input.getCountryId()
                            )
                    );
            // Проверить уникальность с новым countryId и текущим именем (если имя не пустое)
            if (!newCountry
                    .equals(city.getCountry())
            ) {
                city.setCountry(newCountry);
            }
        }

        return cityRepository.save(city);
    }

    @Transactional(readOnly = false)
    public Boolean deleteById(Integer id) {
        City city = cityRepository
                .findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "City not found with id: "
                                        + id
                        )
                );

        // Удаление города, если на него ссылаются другие таблицы (shop, warehouse, supplier, customer, employee),
        // произойдёт ошибка целостности, если в БД стоит ON DELETE RESTRICT (как в схеме).
        cityRepository.delete(city);
        return true;
    }
}
