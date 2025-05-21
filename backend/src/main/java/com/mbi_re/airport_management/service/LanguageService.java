package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.model.Language;
import com.mbi_re.airport_management.repository.LanguageRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    /**
     * Retrieves all languages associated with the current tenant.
     * Cached for performance, assuming language definitions do not change frequently.
     *
     * @return a list of language entities for the current tenant
     */
    @Cacheable("languages")
    public List<Language> getAllLanguages() {
        String tenantId = TenantContext.getTenantId();
        return languageRepository.findAllByTenantId(tenantId);
    }

    /**
     * Saves a new language entity under the current tenant.
     *
     * @param language the language to persist
     * @return the persisted language entity
     */
    public Language saveLanguage(Language language) {
        language.setTenantId(TenantContext.getTenantId());
        return languageRepository.save(language);
    }

    /**
     * Deletes a language by ID, scoped to the current tenant.
     * Throws IllegalArgumentException if not found.
     *
     * @param id the ID of the language to delete
     */
    public void deleteLanguage(Long id) {
        String tenantId = TenantContext.getTenantId();
        Language language = languageRepository.findByIdAndTenantId(id, tenantId);
        if (language == null) {
            throw new IllegalArgumentException("Language not found or does not belong to current tenant");
        }
        languageRepository.delete(language);
    }
}
