package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.AnnouncementDTO;
import com.mbi_re.airport_management.model.Announcement;
import com.mbi_re.airport_management.repository.AnnouncementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AnnouncementServiceTest {

    private AnnouncementService announcementService;
    private AnnouncementRepository announcementRepository;

    @BeforeEach
    void setUp() {
        announcementRepository = mock(AnnouncementRepository.class);
        announcementService = new AnnouncementService();

        // Inject mock using reflection
        setPrivateField(announcementService, "announcementRepository", announcementRepository);
    }

    @Test
    void testGetAnnouncementsByTenant() {
        String tenantId = "tenant123";
        Announcement a1 = new Announcement();
        a1.setTitle("Notice A");
        a1.setMessage("Message A");
        a1.setTenantId(tenantId);
        a1.setCreatedAt(LocalDateTime.now());

        Announcement a2 = new Announcement();
        a2.setTitle("Notice B");
        a2.setMessage("Message B");
        a2.setTenantId(tenantId);
        a2.setCreatedAt(LocalDateTime.now());

        when(announcementRepository.findByTenantId(tenantId)).thenReturn(Arrays.asList(a1, a2));

        List<AnnouncementDTO> result = announcementService.getAnnouncementsByTenant(tenantId);

        assertEquals(2, result.size());
        assertEquals("Notice A", result.get(0).getTitle());
        assertEquals("Message B", result.get(1).getMessage());
        verify(announcementRepository, times(1)).findByTenantId(tenantId);
    }

    @Test
    void testSaveAnnouncement() {
        AnnouncementDTO dto = new AnnouncementDTO();
        dto.setTitle("Test Title");
        dto.setMessage("Test Message");
        dto.setTenantId("tenant456");

        Announcement savedEntity = new Announcement();
        savedEntity.setTitle(dto.getTitle());
        savedEntity.setMessage(dto.getMessage());
        savedEntity.setTenantId(dto.getTenantId());
        savedEntity.setCreatedAt(LocalDateTime.now());

        when(announcementRepository.save(any(Announcement.class))).thenReturn(savedEntity);

        AnnouncementDTO savedDTO = announcementService.saveAnnouncement(dto);

        assertEquals(dto.getTitle(), savedDTO.getTitle());
        assertEquals(dto.getMessage(), savedDTO.getMessage());
        assertEquals(dto.getTenantId(), savedDTO.getTenantId());
        assertNotNull(savedDTO.getCreatedAt());
        verify(announcementRepository, times(1)).save(any(Announcement.class));
    }

    private void setPrivateField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set field: " + fieldName, e);
        }
    }
}
