package roz.tan.retail.crud.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roz.tan.retail.crud.repositories.BrandRepository;
import roz.tan.retail.crud.repositories.CountryRepository;
import roz.tan.retail.models.Brand;
import roz.tan.retail.models.Country;
import roz.tan.retail.utils.dto.BrandInput;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class BrandService {

    @Autowired
    @Qualifier("brandRepository")
    private BrandRepository brandRepository;

    @Autowired
    @Qualifier("countryRepository")
    private CountryRepository countryRepository;

    @Transactional(readOnly = false)
    public Brand save(BrandInput input) {
        // 1. Валидация имени
        if (input.getName() == null
                || input.getName().isBlank()
        ) {
            throw new IllegalArgumentException(
                    "Brand name must not be null or blank"
            );
        }

        // 2. Загрузка страны (если указана)
        Country country = null;
        if (input.getCountryId() != null) {
            country = countryRepository
                    .findById(input.getCountryId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Country not found with id: "
                                    + input.getCountryId()
                            )
                    );
        }

        // 4. Создание и сохранение бренда
        Brand brand = Brand
                .builder()
                .name(input.getName())
                .country(country)
                .isActive(true)   // по умолчанию активен
                .build();

        return brandRepository
                .save(brand);
    }

    @Transactional(readOnly = false)
    public Brand update(
            Integer id,
            BrandInput input
    ) {
        // 1. Проверка существования бренда
        Brand brand = brandRepository
                .findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Brand not found with id: "
                                        + id
                        )
                );

        // 2. Обновление имени (если передано и изменилось)
        if (input.getName() != null
                && !input.getName().isBlank()
        ) {
            if (!input.getName()
                    .equals(brand.getName())
            ) {
                brand.setName(input.getName());
            }
        }

        // 3. Обновление страны (если countryId передан явно)
        if (input.getCountryId() != null) {
            Country country = countryRepository
                    .findById(input.getCountryId()
                    )
                    .orElseThrow(() ->
                            new EntityNotFoundException(
                                    "Country not found with id: "
                                            + input.getCountryId()
                            )
                    );
            brand.setCountry(country);
        } else if (input.getCountryId() == null
                && brand.getCountry() != null
        ) {
            // Явное указание null для сброса страны
            brand.setCountry(null);
        }

        // Поле isActive не обновляется через BrandInput, оставляем как есть

        return brandRepository
                .save(brand);
    }

    @Transactional(readOnly = false)
    public Boolean deleteById(Integer id) {
        // 1. Проверка существования
        Brand brand = brandRepository
                .findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Brand not found with id: "
                                        + id
                        )
                );

        // 2. Удаление (ссылки в продуктах установятся в NULL благодаря ON DELETE SET NULL)
        brandRepository
                .delete(brand);
        return true;
    }

    public Set<Brand> findAllByIds(List<Integer> ids) {
        return brandRepository
                .findAllByIds(ids);
    }

}
