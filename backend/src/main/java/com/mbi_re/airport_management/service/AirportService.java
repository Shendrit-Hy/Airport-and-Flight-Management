package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.AirportDTO;
import com.mbi_re.airport_management.model.Airport;
import com.mbi_re.airport_management.model.City;
import com.mbi_re.airport_management.model.Country;
import com.mbi_re.airport_management.repository.AirportRepository;
import com.mbi_re.airport_management.repository.CityRepository;
import com.mbi_re.airport_management.repository.CountryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing airport operations with tenant awareness.
 */
@Service
public class AirportService {

    private final AirportRepository airportRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    public AirportService(AirportRepository airportRepository,
                          CountryRepository countryRepository,
                          CityRepository cityRepository) {
        this.airportRepository = airportRepository;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
    }

    /**
     * Retrieves all airports associated with a specific tenant.
     * Results are cached for better performance.
     *
     * @param tenantId the tenant identifier
     * @return list of airports
     */
    @Cacheable(value = "airports", key = "#tenantId")
    public List<Airport> getAllAirports(String tenantId) {
        return airportRepository.findByTenantId(tenantId);
    }

    /**
     * Creates a new airport for the given tenant.
     * Cache is invalidated for the tenant to ensure consistency.
     *
     * @param airportDTO the airport data
     * @param tenantId   the tenant identifier
     * @return created airport entity
     */
    @CacheEvict(value = "airports", key = "#tenantId")
    public Airport createAirport(AirportDTO airportDTO, String tenantId) {
        Country country = countryRepository.findById(airportDTO.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("Country not found"));

        City city = cityRepository.findById(airportDTO.getCityId())
                .orElseThrow(() -> new IllegalArgumentException("City not found"));

        Airport airport = new Airport();
        airport.setName(airportDTO.getName());
        airport.setCode(airportDTO.getCode());
        airport.setCountry(country);
        airport.setCity(city);
        airport.setTimezone(airportDTO.getTimezone());
        airport.setTenantId(tenantId);

        return airportRepository.save(airport);
    }

    /**
     * Deletes an airport by ID and tenant.
     * Ensures the airport belongs to the tenant before deletion.
     * Evicts cache for the tenant.
     *
     * @param id       the airport ID
     * @param tenantId the tenant identifier
     */
    @CacheEvict(value = "airports", key = "#tenantId")
    public void deleteAirport(Long id, String tenantId) {
        airportRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Airport not found or does not belong to tenant"));
        airportRepository.deleteByIdAndTenantId(id, tenantId);
    }
}
