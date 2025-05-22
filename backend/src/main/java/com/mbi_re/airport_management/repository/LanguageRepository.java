package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    /**
     * Finds all languages associated with the given tenant ID.
     *
     * @param tenantId the tenant ID to filter by
     * @return a list of languages for the specified tenant
     */
    List<Language> findAllByTenantId(String tenantId);

    /**
     * Finds a language by its ID and tenant ID.
     *
     * @param id       the ID of the language
     * @param tenantId the tenant ID
     * @return the language if found, else null
     */
    Language findByIdAndTenantId(Long id, String tenantId);
}