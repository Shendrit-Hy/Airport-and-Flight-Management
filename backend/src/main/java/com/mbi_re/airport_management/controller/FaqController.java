package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.FaqDTO;
import com.mbi_re.airport_management.service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faqs")
@RequiredArgsConstructor
public class FaqController {

    @Autowired
    private  FaqService faqService;

    @GetMapping
    public List<FaqDTO> getFaqs(@RequestHeader("X-Tenant-ID") String tenantId) {
        return faqService.getFaqsByTenant(tenantId);
    }

    @PostMapping
    public FaqDTO saveFaq(@RequestBody FaqDTO faqDTO, @RequestHeader("X-Tenant-ID") String tenantId) {
        faqDTO.setTenantId(tenantId);
        return faqService.saveFaq(faqDTO);
    }
}
