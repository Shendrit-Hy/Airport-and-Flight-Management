package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository për menaxhimin e entitetit {@link Airline}.
 * Siguron metoda të personalizuara për operacione që varen nga tenantId.
 */
public interface AirlineRepository extends JpaRepository<Airline, Long> {

    /**
     * Kontrollon nëse ekziston një kompani ajrore me emrin e dhënë për një tenant specifik.
     *
     * @param name     emri i kompanisë ajrore
     * @param tenantId identifikuesi i tenantit
     * @return {@code true} nëse ekziston, përndryshe {@code false}
     */boolean existsByNameAndTenantId(String name, String tenantId);/**
     * Gjen të gjitha kompanitë ajrore për një tenant të caktuar.
     *
     * @param tenantId identifikuesi i tenantit
     * @return listë e kompanive ajrore përkatëse
     */
    List<Airline> findByTenantId(String tenantId);

    /**
     * Gjen një kompani ajrore sipas ID-së dhe tenantId-it të saj.
     *
     * @param airlineId ID e kompanisë ajrore
     * @param tenantId  identifikuesi i tenantit
     * @return {@link Optional} me kompaninë ajrore, nëse ekziston
     */
    Optional<Airline> findByIdAndTenantId(Long airlineId, String tenantId);
}
