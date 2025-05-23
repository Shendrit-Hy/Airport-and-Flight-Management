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
     * Retrieve all languages for the current tenant.
     */
    @Cacheable("languages")
    public List<Language> getAllLanguages() {
        String tenantId = TenantContext.getTenantId();
        return languageRepository.findAllByTenantId(tenantId);
    }

    /**
     * Save a new language for the current tenant.
     */
    public Language saveLanguage(Language language) {
        language.setTenantId(TenantContext.getTenantId());
        return languageRepository.save(language);
    }

    /**
     * Create a Language entity from DTO and save it.
     */
    public Language createFromDTO(LanguageDTO dto, String tenantId) {
        Language language = new Language();
        language.setName(dto.getName());
        language.setCode(dto.getCode());
        language.setTenantId(tenantId);
        return languageRepository.save(language);
    }

    /**
     * Delete a language by ID if it belongs to the current tenant.
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
