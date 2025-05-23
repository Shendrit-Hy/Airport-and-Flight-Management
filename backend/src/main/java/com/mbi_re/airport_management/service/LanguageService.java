package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.LanguageDTO;
import com.mbi_re.airport_management.model.Language;
import com.mbi_re.airport_management.repository.LanguageRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    /**
     * Retrieves all languages associated with the current tenant.
     * <p>
     * The tenant ID is resolved from the {@link TenantContext}.
     *
     * @return a list of {@link Language} entities belonging to the current tenant
     */
    public List<Language> getAllLanguages() {
        String tenantId = TenantContext.getTenantId();
        return languageRepository.findAllByTenantId(tenantId);
    }

    /**
     * Saves a new language entity for the current tenant.
     * <p>
     * Sets the tenant ID on the language entity before persisting.
     *
     * @param language the {@link Language} entity to save
     * @return the saved {@link Language} entity with tenant ID set
     */
    public Language saveLanguage(Language language) {
        language.setTenantId(TenantContext.getTenantId());
        return languageRepository.save(language);
    }

    /**
     * Creates and saves a new language entity from the given DTO and tenant ID.
     *
     * @param dto      the {@link LanguageDTO} containing language data
     * @param tenantId the tenant ID to associate with the new language
     * @return the saved {@link Language} entity
     */
    public Language createFromDTO(LanguageDTO dto, String tenantId) {
        Language language = new Language();
        language.setName(dto.getName());
        language.setCode(dto.getCode());
        language.setTenantId(tenantId);
        return languageRepository.save(language);
    }

    /**
     * Deletes a language by its ID if it belongs to the current tenant.
     * <p>
     * Uses tenant ID from {@link TenantContext} to scope deletion.
     * Throws {@link IllegalArgumentException} if language is not found or does not belong to the tenant.
     *
     * @param id the ID of the language to delete
     * @throws IllegalArgumentException if language does not exist or belongs to another tenant
     */
    @Transactional
    public void deleteLanguage(Long id) {
        String tenantId = TenantContext.getTenantId();

        Language language = languageRepository
                .findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Language not found or does not belong to current tenant"));

        languageRepository.delete(language);
    }
}
