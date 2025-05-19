package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.FaqDTO;
import com.mbi_re.airport_management.model.Faq;
import com.mbi_re.airport_management.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FaqService {

    @Autowired
    private FaqRepository faqRepository;

    public List<FaqDTO> getFaqsByTenant(String tenantId) {
        return faqRepository.findAllByTenantId(tenantId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public FaqDTO saveFaq(FaqDTO dto) {
        Faq faq = toEntity(dto);
        Faq saved = faqRepository.save(faq);
        return toDTO(saved);
    }

    private FaqDTO toDTO(Faq faq) {
        FaqDTO dto = new FaqDTO();
        dto.setId(faq.getId());
        dto.setQuestion(faq.getQuestion());
        dto.setAnswer(faq.getAnswer());
        dto.setTenantId(faq.getTenantId());
        return dto;
    }

    private Faq toEntity(FaqDTO dto) {
        Faq faq = new Faq();
        faq.setId(dto.getId());
        faq.setQuestion(dto.getQuestion());
        faq.setAnswer(dto.getAnswer());
        faq.setTenantId(dto.getTenantId());
        return faq;
    }
}

