package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.CityDTO;
import com.mbi_re.airport_management.model.City;
import com.mbi_re.airport_management.model.Country;
import com.mbi_re.airport_management.repository.CityRepository;
import com.mbi_re.airport_management.repository.CountryRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing city-related operations within a tenant's scope.
 */
@Service
public class CityService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    public CityService(CityRepository cityRepository, CountryRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }

    /**
     * Retrieves all cities for the current tenant.
     * <p>
     * The results are cached by tenant ID.
     *
     * @return list of CityDTOs
     */
    @Cacheable(value = "cities", key = "T(com.mbi_re.airport_management.config.TenantContext).getTenantId() + '_all'")
    public List<CityDTO> getAllCities() {
        String tenantId = TenantContext.getTenantId();
        return cityRepository.findAllByTenantId(tenantId).stream()
                .map(c -> new CityDTO(c.getId(), c.getName(), c.getCountry().getId()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all cities for a given country and current tenant.
     * <p>
     * The results are cached per country and tenant ID.
     *
     * @param countryId the country ID
     * @return list of CityDTOs
     */
    @Cacheable(value = "cities", key = "T(com.mbi_re.airport_management.config.TenantContext).getTenantId() + '_country_' + #countryId")
    public List<CityDTO> getCitiesByCountry(Long countryId) {
        String tenantId = TenantContext.getTenantId();
        return cityRepository.findByCountryIdAndTenantId(countryId, tenantId).stream()
                .map(c -> new CityDTO(c.getId(), c.getName(), c.getCountry().getId()))
                .collect(Collectors.toList());
    }

    /**
     * Creates a new city for the current tenant.
     * <p>
     * Cache for all cities and cities by country is evicted upon creation.
     *
     * @param dto the CityDTO to create
     * @return the created CityDTO
     */
    @CacheEvict(value = "cities", allEntries = true)
    public CityDTO createCity(CityDTO dto) {
        String tenantId = TenantContext.getTenantId();

        Country country = countryRepository.findById(dto.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found"));

        City city = new City();
        city.setName(dto.getName());
        city.setCountry(country);
        city.setTenantId(tenantId);

        City saved = cityRepository.save(city);
        return new CityDTO(saved.getId(), saved.getName(), saved.getCountry().getId());
    }

    /**
     * Deletes a city by ID for the current tenant.
     * <p>
     * Evicts all relevant cache entries for cities.
     *
     * @param id the ID of the city to delete
     */
    @CacheEvict(value = "cities", allEntries = true)
    @Transactional
    public void deleteCity(Long id) {
        String tenantId = TenantContext.getTenantId();
        cityRepository.deleteByIdAndTenantId(id, tenantId);
    }
}
