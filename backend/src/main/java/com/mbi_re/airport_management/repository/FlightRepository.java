package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * {@code FlightRepository} ofron operacione CRUD për entitetet {@link Flight},
 * me filtrim të të dhënave sipas tenant-it në një sistem multi-tenant.
 */
@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    /**
     * Gjen të gjitha fluturimet që fillojnë nga një datë e caktuar e tutje për një tenant specifik.
     *
     * @param date     data fillestare e fluturimit
     * @param tenantId identifikuesi i tenant-it
     * @return listë me fluturimet e gjetura
     */
    List<Flight> findByFlightDateGreaterThanEqualAndTenantId(LocalDate date, String tenantId);

    /**
     * Gjen një fluturim bazuar në numrin e fluturimit.
     *
     * @param flightNumber numri i fluturimit
     * @return objekti {@link Flight} nëse gjendet, përndryshe {@code null}
     */
    Flight findByFlightNumber(String flightNumber);

    /**
     * Gjen një fluturim sipas ID-së dhe tenantId.
     *
     * @param id       ID-ja e fluturimit
     * @param tenantId identifikuesi i tenant-it
     * @return {@link Optional} që përmban fluturimin nëse ekziston
     */
    Optional<Flight> findByIdAndTenantId(Long id, String tenantId);

    /**
     * Gjen të gjitha fluturimet që i përkasin një tenant-i të caktuar.
     *
     * @param tenantId identifikuesi i tenant-it
     * @return listë me fluturime
     */
    List<Flight> findByTenantId(String tenantId);

    /**
     * Kthen të gjitha fluturimet që përputhen me kriteret e filtrimit për një tenant:
     * aeroporti i nisjes, aeroporti i mbërritjes, periudha e datave dhe numri i vendeve të lira.
     *
     * @param tenantId   identifikuesi i tenant-it
     * @param from       aeroporti i nisjes
     * @param to         aeroporti i mbërritjes
     * @param start      data e fillimit
     * @param end        data e përfundimit
     * @param passengers numri minimal i vendeve të lira
     * @return listë me fluturimet që përputhen me kërkesën
     */
    List<Flight> findByTenantIdAndDepartureAirportIgnoreCaseAndArrivalAirportIgnoreCaseAndFlightDateBetweenAndAvailableSeatGreaterThanEqual(
            String tenantId, String from, String to, LocalDate start, LocalDate end, int passengers
    );
}
