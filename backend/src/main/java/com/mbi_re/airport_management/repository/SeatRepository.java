package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * {@code SeatRepository} ofron operacione për menaxhimin e entiteteve {@link Seat},
 * me filtrime të përshtatshme për secilin tenant në një arkitekturë multi-tenant.
 */
public interface SeatRepository extends JpaRepository<Seat, Long> {

    /**
     * Gjen të gjitha ulëset për një fluturim të caktuar dhe tenant.
     *
     * @param flightId ID-ja e fluturimit
     * @param tenantId identifikuesi i tenant-it
     * @return listë me ulëse përkatëse
     */
    List<Seat> findByFlightIdAndTenantId(Long flightId, String tenantId);

    /**
     * Gjen të gjitha ulëset e lira (jo të rezervuara) për një fluturim dhe tenant.
     *
     * @param flightId ID-ja e fluturimit
     * @param tenantId identifikuesi i tenant-it
     * @return listë me ulëse të disponueshme
     */
    List<Seat> findByFlightIdAndTenantIdAndBookedFalse(Long flightId, String tenantId);

    /**
     * Gjen një ulëse të caktuar sipas ID-së dhe tenant-it përkatës.
     *
     * @param id       ID-ja e ulëses
     * @param tenantId identifikuesi i tenant-it
     * @return {@link Optional} që përmban ulësen nëse ekziston
     */
    Optional<Seat> findByIdAndTenantId(Long id, String tenantId);

    /**
     * Fshin të gjitha ulëset që i përkasin një fluturimi dhe tenant-i të caktuar.
     *
     * @param flightId ID-ja e fluturimit
     * @param tenantId identifikuesi i tenant-it
     */
    void deleteByFlightIdAndTenantId(Long flightId, String tenantId);
}
