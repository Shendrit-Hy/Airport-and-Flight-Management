package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * {@code StaffRepository} ofron metoda për menaxhimin dhe qasjen në entitetet {@link Staff},
 * duke mbështetur filtrimin sipas {@code tenantId} për sisteme me arkitekturë multi-tenant.
 */
@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    /**
     * Gjen një anëtar të stafit sipas ID-së dhe tenant-it.
     *
     * @param id       ID-ja e stafit
     * @param tenantId identifikuesi i tenant-it
     * @return një {@link Optional} që përmban stafin nëse ekziston
     */
    Optional<Staff> findByIdAndTenantId(Long id, String tenantId);

    /**
     * Kthen të gjithë anëtarët e stafit që i përkasin një tenant-i të caktuar.
     *
     * @param tenantId identifikuesi i tenant-it
     * @return listë me entitete {@link Staff} për tenant-in përkatës
     */
    List<Staff> findByTenantId(String tenantId);
}
