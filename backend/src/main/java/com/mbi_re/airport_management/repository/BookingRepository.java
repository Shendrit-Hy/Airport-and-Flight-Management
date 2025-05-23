package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * {@code BookingRepository} ofron qasje në të dhënat e rezervimeve të fluturimeve,
 * me mbështetje për filtrimin sipas tenant-it për aplikacione multi-tenant.
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Gjen të gjitha rezervimet që i përkasin një tenant-i të caktuar.
     *
     * @param tenantId identifikuesi i tenant-it
     * @return listë me rezervimet përkatëse
     */
    List<Booking> findByTenantId(String tenantId);

    /**
     * Gjen një rezervim sipas ID-së dhe tenant ID.
     *
     * @param id ID-ja e rezervimit
     * @param tenantId identifikuesi i tenant-it
     * @return {@link Optional} që përmban rezervimin nëse ekziston
     */
    Optional<Booking> findByIdAndTenantId(Long id, String tenantId);
}
