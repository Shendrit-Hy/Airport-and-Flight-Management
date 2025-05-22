package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on Flight entities.
 * Includes methods scoped to specific tenants.
 */
@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    /**
     * Retrieves all flights for a tenant that are scheduled from the specified date onward.
     *
     * @param date the date from which to start retrieving flights
     * @param tenantId the tenant identifier
     * @return list of flights
     */
    List<Flight> findByFlightDateGreaterThanEqualAndTenantId(LocalDate date, String tenantId);

    /**
     * Finds a flight by its flight number.
     *
     * @param flightNumber the flight number
     * @return the flight if found
     */
    Flight findByFlightNumber(String flightNumber);

    /**
     * Finds a flight by its ID and tenant ID.
     *
     * @param id the flight ID
     * @param tenantId the tenant ID
     * @return an optional containing the flight if found
     */
    Optional<Flight> findByIdAndTenantId(Long id, String tenantId);

    /**
     * Finds all flights associated with a specific tenant.
     *
     * @param tenantId the tenant ID
     * @return list of flights
     */
    List<Flight> findByTenantId(String tenantId);

    /**
     * Retrieves flights matching filtering criteria for a tenant.
     *
     * @param tenantId the tenant ID
     * @param from departure airport
     * @param to arrival airport
     * @param start start date
     * @param end end date
     * @param passengers minimum available seats
     * @return list of matching flights
     */
    List<Flight> findByTenantIdAndDepartureAirportIgnoreCaseAndArrivalAirportIgnoreCaseAndFlightDateBetweenAndAvailableSeatGreaterThanEqual(
            String tenantId, String from, String to, LocalDate start, LocalDate end, int passengers
    );
}
