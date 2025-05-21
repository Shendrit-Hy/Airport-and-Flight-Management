package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.FaqDTO;
import com.mbi_re.airport_management.model.Faq;
import com.mbi_re.airport_management.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
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

    private final FaqRepository faqRepository;

    /**
     * Retrieves all FAQs for a specific tenant.
     *
     * @param tenantId the tenant identifier
     * @return list of FAQ DTOs
     */
    @Cacheable(value = "faqs", key = "#tenantId")
    public List<FaqDTO> getFaqsByTenant(String tenantId) {
        return faqRepository.findAllByTenantId(tenantId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Saves or updates an FAQ for a specific tenant.
     * Also evicts the cached FAQs for the tenant.
     *
     * @param dto the FAQ DTO to save
     * @return the saved FAQ DTO
     */
    @CacheEvict(value = "faqs", key = "#dto.tenantId")
    public FaqDTO saveFaq(FaqDTO dto) {
        Faq faq = toEntity(dto);
        Faq saved = faqRepository.save(faq);
        return toDTO(saved);
    }

    /**
     * Deletes an FAQ by ID and tenant ID.
     * Also evicts the cached FAQs for the tenant.
     *
     * @param id the FAQ ID
     * @param tenantId the tenant identifier
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
     * Converts an FAQ entity to its DTO representation.
     *
     * @param faq the FAQ entity
     * @return the FAQ DTO
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
     * Converts an FAQ DTO to its entity representation.
     *
     * @param dto the FAQ DTO
     * @return the FAQ entity
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
