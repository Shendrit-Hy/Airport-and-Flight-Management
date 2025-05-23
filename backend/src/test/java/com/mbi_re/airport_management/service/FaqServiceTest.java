package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.FaqDTO;
import com.mbi_re.airport_management.model.Faq;
import com.mbi_re.airport_management.repository.FaqRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FaqServiceTest {

    @Mock
    private FaqRepository faqRepository;

    @InjectMocks
    private FaqService faqService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFaqsByTenant_ReturnsFaqDTOList() {
        String tenantId = "tenant1";

        Faq faq1 = new Faq();
        faq1.setId(1L);
        faq1.setQuestion("Q1");
        faq1.setAnswer("A1");
        faq1.setTenantId(tenantId);

        Faq faq2 = new Faq();
        faq2.setId(2L);
        faq2.setQuestion("Q2");
        faq2.setAnswer("A2");
        faq2.setTenantId(tenantId);

        when(faqRepository.findAllByTenantId(tenantId)).thenReturn(Arrays.asList(faq1, faq2));

        List<FaqDTO> faqs = faqService.getFaqsByTenant(tenantId);

        assertEquals(2, faqs.size());
        assertEquals("Q1", faqs.get(0).getQuestion());
        assertEquals("A2", faqs.get(1).getAnswer());
    }

    @Test
    void testSaveFaq_SavesAndReturnsDto() {
        FaqDTO dto = new FaqDTO();
        dto.setId(null);
        dto.setQuestion("Q1");
        dto.setAnswer("A1");
        dto.setTenantId("tenant1");

        Faq savedFaq = new Faq();
        savedFaq.setId(1L);
        savedFaq.setQuestion("Q1");
        savedFaq.setAnswer("A1");
        savedFaq.setTenantId("tenant1");

        when(faqRepository.save(any(Faq.class))).thenReturn(savedFaq);

        FaqDTO result = faqService.saveFaq(dto);

        assertNotNull(result.getId());
        assertEquals("Q1", result.getQuestion());
        assertEquals("A1", result.getAnswer());
    }

    @Test
    void testDeleteFaq_DeletesWhenFound() {
        Long faqId = 1L;
        String tenantId = "tenant1";

        Faq faq = new Faq();
        faq.setId(faqId);
        faq.setQuestion("Q1");
        faq.setAnswer("A1");
        faq.setTenantId(tenantId);

        when(faqRepository.findByIdAndTenantId(faqId, tenantId)).thenReturn(Optional.of(faq));

        faqService.deleteFaq(faqId, tenantId);

        verify(faqRepository, times(1)).delete(faq);
    }

    @Test
    void testDeleteFaq_ThrowsWhenNotFound() {
        Long faqId = 99L;
        String tenantId = "tenant1";

        when(faqRepository.findByIdAndTenantId(faqId, tenantId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> faqService.deleteFaq(faqId, tenantId));

        assertEquals("FAQ not found or tenant mismatch.", ex.getMessage());
    }
}
