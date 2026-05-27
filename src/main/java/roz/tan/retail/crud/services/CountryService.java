package roz.tan.retail.crud.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roz.tan.retail.crud.repositories.CountryRepository;
import roz.tan.retail.models.Country;
import roz.tan.retail.utils.dto.CountryInput;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true)
public class CountryService {

    private static final Pattern CODE_PATTERN = Pattern.compile("^[A-Z]{3}$");

    @Autowired
    @Qualifier("countryRepository")
    private CountryRepository countryRepository;

    public Set<Country> findAllByIds(List<Integer> ids) {
        return countryRepository.findAllByIds(ids);
    }

    @Transactional(readOnly = false)
    public Country save(CountryInput input) {
        // 1. Валидация имени
        if (input.getName() == null || input.getName().isBlank()) {
            throw new IllegalArgumentException("Country name must not be null or blank");
        }

        // 2. Валидация кода (если указан)
        String code = null;
        if (input.getCode() != null && !input.getCode().isBlank()) {
            String trimmedCode = input.getCode().trim().toUpperCase();
            if (!CODE_PATTERN.matcher(trimmedCode).matches()) {
                throw new IllegalArgumentException("Country code must be exactly 3 uppercase letters");
            }
            code = trimmedCode;
        }

        Country country = Country.builder()
                .name(input.getName())
                .code(code)
                .build();

        return countryRepository.save(country);
    }

    @Transactional(readOnly = false)
    public Country update(Integer id, CountryInput input) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country not found with id: " + id));

        // Обновление имени
        if (input.getName() != null && !input.getName().isBlank()) {
            if (!input.getName().equals(country.getName())) {
                country.setName(input.getName());
            }
        } else if (input.getName() != null && input.getName().isBlank()) {
            throw new IllegalArgumentException("Country name cannot be blank");
        }

        // Обновление кода
        if (input.getCode() != null) {
            String newCode = input.getCode().isBlank() ? null : input.getCode().trim().toUpperCase();
            if (newCode != null) {
                if (!CODE_PATTERN.matcher(newCode).matches()) {
                    throw new IllegalArgumentException("Country code must be exactly 3 uppercase letters");
                }
            }
            country.setCode(newCode);
        }

        return countryRepository.save(country);
    }

    @Transactional
    public Boolean deleteById(Integer id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country not found with id: " + id));

        countryRepository.delete(country);
        return true;
    }
}
