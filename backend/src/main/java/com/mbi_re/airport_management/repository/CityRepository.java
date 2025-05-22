package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for managing City entities with tenant-specific access.
 */
public interface CityRepository extends JpaRepository<City, Long> {

    /**
     * Finds all cities for a given tenant.
     *
     * @param tenantId the tenant identifier
     * @return list of cities for the tenant
     */
    List<City> findAllByTenantId(String tenantId);

    /**
     * Finds all cities by country ID for a given tenant.
     *
     * @param countryId the country ID
     * @param tenantId  the tenant identifier
     * @return list of cities for the specified country and tenant
     */
    List<City> findByCountryIdAndTenantId(Long countryId, String tenantId);

    /**
     * Deletes a city by ID and tenant ID.
     *
     * @param id       the city ID
     * @param tenantId the tenant identifier
     */
    void deleteByIdAndTenantId(Long id, String tenantId);
}
