package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * {@code PassengerRepository} ofron operacione për qasje dhe manipulim të të dhënave
 * për entitetin {@link Passenger}, me izolim sipas {@code tenantId} për mbështetje multi-tenant.
 */
@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    /**
     * Gjen të gjithë pasagjerët që i përkasin një tenant-i të caktuar.
     *
     * @param tenantId identifikuesi i tenant-it
     * @return listë me pasagjerë për tenant-in e dhënë
     */
    List<Passenger> findAllByTenantId(String tenantId);

    /**
     * Gjen një pasagjer sipas ID-së dhe tenant-it përkatës.
     *
     * @param id       ID-ja e pasagjerit
     * @param tenantId identifikuesi i tenant-it
     * @return {@link Optional} që përmban pasagjerin nëse ekziston
     */
    Optional<Passenger> findByIdAndTenantId(Long id, String tenantId);

    /**
     * Fshin një pasagjer duke përdorur ID-në dhe tenant-id-in përkatës.
     *
     * @param id       ID-ja e pasagjerit
     * @param tenantId identifikuesi i tenant-it
     */
    void deleteByIdAndTenantId(Long id, String tenantId);
}
