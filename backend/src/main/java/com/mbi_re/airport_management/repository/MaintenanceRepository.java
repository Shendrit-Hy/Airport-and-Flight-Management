package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * {@code MaintenanceRepository} ofron metoda për menaxhimin e entiteteve {@link Maintenance}
 * me mbështetje për filtrimin sipas {@code tenantId}, në një arkitekturë multi-tenant.
 */
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    /**
     * Gjen të gjitha rastet e mirëmbajtjes për një aeroport dhe status të caktuar,
     * të filtruar sipas tenant-it.
     *
     * @param airportID kodi i aeroportit
     * @param status    statusi i mirëmbajtjes (p.sh., "PENDING", "COMPLETED")
     * @param tenantId  identifikuesi i tenant-it
     * @return listë me rastet përputhëse të mirëmbajtjes
     */
    List<Maintenance> findByAirportIDAndStatusAndTenantId(String airportID, String status, String tenantId);

    /**
     * Gjen të gjitha rastet e mirëmbajtjes për një aeroport specifik dhe tenant të caktuar.
     *
     * @param airportID kodi i aeroportit
     * @param tenantId  identifikuesi i tenant-it
     * @return listë me rastet e mirëmbajtjes
     */
    List<Maintenance> findByAirportIDAndTenantId(String airportID, String tenantId);

    /**
     * Gjen të gjitha rastet e mirëmbajtjes për një status të dhënë dhe tenant.
     *
     * @param status   statusi i mirëmbajtjes
     * @param tenantId identifikuesi i tenant-it
     * @return listë me mirëmbajtje që kanë statusin e dhënë
     */
    List<Maintenance> findByStatusAndTenantId(String status, String tenantId);

    /**
     * Gjen të gjitha rastet e mirëmbajtjes për një tenant specifik.
     *
     * @param tenantId identifikuesi i tenant-it
     * @return listë me mirëmbajtje për tenant-in përkatës
     */
    List<Maintenance> findAllByTenantId(String tenantId);

    /**
     * Gjen një rast mirëmbajtjeje sipas ID-së dhe tenant-it.
     *
     * @param id       ID-ja e mirëmbajtjes
     * @param tenantId identifikuesi i tenant-it
     * @return objekti {@link Maintenance} nëse ekziston, përndryshe {@code null}
     */
    Maintenance findByIdAndTenantId(Long id, String tenantId);
}
