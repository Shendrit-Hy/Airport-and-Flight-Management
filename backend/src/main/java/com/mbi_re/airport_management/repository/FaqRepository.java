package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * {@code FaqRepository} është një interface për qasje në entitetet {@link Faq},
 * me filtrim të të dhënave sipas tenant-it për mbështetje në arkitekturë multi-tenant.
 */
@Repository
public interface FaqRepository extends JpaRepository<Faq, Long> {

    /**
     * Gjen të gjitha pyetjet dhe përgjigjet (FAQ) për një tenant specifik.
     *
     * @param tenantId identifikuesi i tenant-it
     * @return listë me të gjitha FAQ-të e tenant-it
     */
    List<Faq> findAllByTenantId(String tenantId);

    /**
     * Gjen një FAQ specifik sipas ID-së dhe tenant-it përkatës.
     *
     * @param id       ID-ja e FAQ-së
     * @param tenantId identifikuesi i tenant-it
     * @return {@link Optional} me objektin {@link Faq} nëse ekziston
     */
    Optional<Faq> findByIdAndTenantId(Long id, String tenantId);
}
