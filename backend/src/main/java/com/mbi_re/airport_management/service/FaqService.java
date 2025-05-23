package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.FaqDTO;
import com.mbi_re.airport_management.model.Faq;
import com.mbi_re.airport_management.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing FAQs with tenant-based filtering and caching.
 */
@Service
@RequiredArgsConstructor
public class FaqService {

    @Autowired
    private FaqRepository faqRepository;

    /**
     * Retrieves all FAQs belonging to the specified tenant.
     * <p>
     * The result is cached per tenant to improve performance.
     *
     * @param tenantId the tenant identifier
     * @return a list of {@link FaqDTO} objects representing the FAQs for the tenant
     */
    @Cacheable(value = "faqs", key = "#tenantId")
    public List<FaqDTO> getFaqsByTenant(String tenantId) {
        return faqRepository.findAllByTenantId(tenantId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Saves or updates an FAQ for the given tenant.
     * <p>
     * Evicts the cached FAQs for the tenant after saving to ensure cache consistency.
     *
     * @param dto the FAQ DTO to save or update
     * @return the saved {@link FaqDTO} object
     */
    @CacheEvict(value = "faqs", key = "#dto.tenantId")
    public FaqDTO saveFaq(FaqDTO dto) {
        Faq faq = toEntity(dto);
        Faq saved = faqRepository.save(faq);
        return toDTO(saved);
    }

    /**
     * Deletes an FAQ by its ID and tenant ID.
     * <p>
     * Evicts the cached FAQs for the tenant after deletion.
     * Throws a runtime exception if the FAQ is not found or the tenant does not match.
     *
     * @param id the FAQ ID to delete
     * @param tenantId the tenant identifier
     * @throws RuntimeException if the FAQ is not found or tenant mismatch occurs
     */
    @CacheEvict(value = "faqs", key = "#tenantId")
    public void deleteFaq(Long id, String tenantId) {
        Optional<Faq> faqOpt = faqRepository.findByIdAndTenantId(id, tenantId);
        if (faqOpt.isPresent()) {
            faqRepository.delete(faqOpt.get());
        } else {
            throw new RuntimeException("FAQ not found or tenant mismatch.");
        }
    }

    /**
     * Converts an {@link Faq} entity to its data transfer object (DTO) representation.
     *
     * @param faq the FAQ entity to convert
     * @return the corresponding {@link FaqDTO}
     */
    private FaqDTO toDTO(Faq faq) {
        FaqDTO dto = new FaqDTO();
        dto.setId(faq.getId());
        dto.setQuestion(faq.getQuestion());
        dto.setAnswer(faq.getAnswer());
        dto.setTenantId(faq.getTenantId());
        return dto;
    }

    /**
     * Converts a {@link FaqDTO} to its entity representation.
     *
     * @param dto the FAQ DTO to convert
     * @return the corresponding {@link Faq} entity
     */
    private Faq toEntity(FaqDTO dto) {
        Faq faq = new Faq();
        faq.setId(dto.getId());
        faq.setQuestion(dto.getQuestion());
        faq.setAnswer(dto.getAnswer());
        faq.setTenantId(dto.getTenantId());
        return faq;
    }
}
