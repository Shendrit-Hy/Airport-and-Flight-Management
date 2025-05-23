package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.AirportDTO;
import com.mbi_re.airport_management.model.Airport;
import com.mbi_re.airport_management.model.City;
import com.mbi_re.airport_management.model.Country;
import com.mbi_re.airport_management.repository.AirportRepository;
import com.mbi_re.airport_management.repository.CityRepository;
import com.mbi_re.airport_management.repository.CountryRepository;
import jakarta.transaction.Transactional;
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
     * Retrieves all airports associated with the specified tenant.
     * Results are cached for performance optimization.
     *
     * @param tenantId the tenant identifier used to filter airports
     * @return a list of Airport entities belonging to the tenant
     */
    @Cacheable(value = "airports", key = "#tenantId")
    public List<Airport> getAllAirports(String tenantId) {
        return airportRepository.findByTenantId(tenantId);
    }

    /**
     * Creates a new airport for the specified tenant.
     * This method also ensures cache consistency by evicting the tenant's airport cache.
     *
     * @param airportDTO the data transfer object containing airport details
     * @param tenantId   the tenant identifier for multi-tenancy support
     * @return the persisted Airport entity after creation
     * @throws IllegalArgumentException if the specified country or city does not exist
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
     * Deletes an airport by its ID for the specified tenant.
     * Ensures that the airport exists and belongs to the tenant before deletion.
     * The operation is transactional and evicts the tenant's airport cache upon success.
     *
     * @param id       the ID of the airport to delete
     * @param tenantId the tenant identifier to verify ownership
     * @throws IllegalArgumentException if the airport is not found or does not belong to the tenant
     */
    @Transactional
    @CacheEvict(value = "airports", key = "#tenantId")
    public void deleteAirport(Long id, String tenantId) {
        airportRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Airport not found or does not belong to tenant"));
        airportRepository.deleteByIdAndTenantId(id, tenantId);
    }
}
