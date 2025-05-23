package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * {@code LanguageRepository} ofron operacione për menaxhimin e gjuhëve të mbështetura në sistem,
 * me filtrime të përshtatura për tenant-id në një ambient multi-tenant.
 */
public interface LanguageRepository extends JpaRepository<Language, Long> {

    /**
     * Gjen të gjitha gjuhët e disponueshme për një tenant të caktuar.
     *
     * @param tenantId identifikuesi i tenant-it
     * @return listë me gjuhët përkatëse
     */
    List<Language> findAllByTenantId(String tenantId);

    /**
     * Gjen një gjuhë sipas ID-së dhe tenant-it përkatës.
     *
     * @param id       ID-ja e gjuhës
     * @param tenantId identifikuesi i tenant-it
     * @return {@link Optional} që përmban gjuhën nëse ekziston
     */
    Optional<Language> findByIdAndTenantId(Long id, String tenantId);

    /**
     * Gjen të gjitha gjuhët për një tenant. (alternative e {@code findAllByTenantId})
     *
     * @param tenantId identifikuesi i tenant-it
     * @return listë me gjuhë të mbështetura
     */
    List<Language> findByTenantId(String tenantId);
}
