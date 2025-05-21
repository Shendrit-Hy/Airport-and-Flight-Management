package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Country entities with tenant-specific access.
 */
public interface CountryRepository extends JpaRepository<Country, Long> {

    /**
     * Checks if a country with the given name exists for a tenant.
     *
     * @param name     the name of the country
     * @param tenantId the tenant identifier
     * @return true if the country exists for the tenant, false otherwise
     */
    boolean existsByNameAndTenantId(String name, String tenantId);

    /**
     * Finds a country by name for a given tenant.
     *
     * @param name     the name of the country
     * @param tenantId the tenant identifier
     * @return an Optional of Country
     */
    Optional<Country> findByNameAndTenantId(String name, String tenantId);

    /**
     * Retrieves all countries for a specific tenant.
     *
     * @param tenantId the tenant identifier
     * @return list of countries
     */
    List<Country> findAllByTenantId(String tenantId);

    /**
     * Deletes a country by code and tenant ID.
     *
     * @param code     the country code
     * @param tenantId the tenant identifier
     */
    void deleteByCodeAndTenantId(String code, String tenantId);
}
