package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.CountryDTO;
import com.mbi_re.airport_management.model.Country;
import com.mbi_re.airport_management.repository.CountryRepository;
import com.mbi_re.airport_management.config.TenantContext;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for handling country-related operations per tenant.
 */
@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    /**
     * Retrieves all countries for the current tenant.
     * <p>
     * Caches the result by tenant to improve performance.
     *
     * @return list of CountryDTO objects
     */
    @Cacheable(value = "countries", key = "T(com.mbi_re.airport_management.config.TenantContext).getTenantId()")
    public List<CountryDTO> getAllCountries() {
        String tenantId = TenantContext.getTenantId();
        return countryRepository.findAllByTenantId(tenantId).stream()
                .map(c -> new CountryDTO(c.getId(), c.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Creates a new country for the current tenant.
     * <p>
     * Invalidates the cache for countries under the current tenant.
     *
     * @param dto the country to create
     * @return the created CountryDTO
     */
    @CacheEvict(value = "countries", key = "T(com.mbi_re.airport_management.config.TenantContext).getTenantId()")
    public CountryDTO createCountry(CountryDTO dto) {
        String tenantId = TenantContext.getTenantId();

        if (countryRepository.existsByNameAndTenantId(dto.getName(), tenantId)) {
            throw new RuntimeException("Country already exists!");
        }

        Country country = new Country();
        country.setName(dto.getName());
        country.setCode(dto.getCode());
        country.setTenantId(tenantId);

        Country saved = countryRepository.save(country);
        return new CountryDTO(saved.getId(), saved.getName());
    }

    /**
     /**
     * Deletes a country for the current tenant based on the ID.
     * <p>
     * Invalidates the countries cache after deletion.
     *
     * @param id the country ID to delete
     */
    @CacheEvict(value = "countries", key = "T(com.mbi_re.airport_management.config.TenantContext).getTenantId()")
    @Transactional
    public void deleteCountry(Long id) {
        String tenantId = TenantContext.getTenantId();
        countryRepository.deleteByIdAndTenantId(id, tenantId);
    }

}
