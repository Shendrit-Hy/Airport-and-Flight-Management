package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.AnnouncementDTO;
import com.mbi_re.airport_management.model.Announcement;
import com.mbi_re.airport_management.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing tenant-specific announcements.
 */
@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    /**
     * Retrieves all announcements associated with the given tenant.
     * This method is cached for public use (unauthenticated users).
     *
     * @param tenantId the tenant identifier
     * @return list of announcement DTOs
     */
    @Cacheable(value = "announcements", key = "#tenantId")
    public List<AnnouncementDTO> getAnnouncementsByTenant(String tenantId) {
        return announcementRepository.findByTenantId(tenantId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Saves a new announcement for a tenant.
     * Cache is evicted to reflect the new state.
     *
     * @param dto the announcement data transfer object
     * @return saved announcement DTO
     */
    @CacheEvict(value = "announcements", key = "#dto.tenantId")
    public AnnouncementDTO saveAnnouncement(AnnouncementDTO dto) {
        Announcement announcement = toEntity(dto);
        announcement.setCreatedAt(LocalDateTime.now());
        Announcement saved = announcementRepository.save(announcement);
        return toDTO(saved);
    }

    /**
     * Converts an entity to a DTO.
     *
     * @param announcement the announcement entity
     * @return announcement DTO
     */
    private AnnouncementDTO toDTO(Announcement announcement) {
        AnnouncementDTO dto = new AnnouncementDTO();
        dto.setTitle(announcement.getTitle());
        dto.setMessage(announcement.getMessage());
        dto.setTenantId(announcement.getTenantId());
        dto.setCreatedAt(announcement.getCreatedAt());
        return dto;
    }

    /**
     * Converts a DTO to an entity.
     *
     * @param dto the announcement DTO
     * @return announcement entity
     */
    private Announcement toEntity(AnnouncementDTO dto) {
        Announcement a = new Announcement();
        a.setTitle(dto.getTitle());
        a.setMessage(dto.getMessage());
        a.setTenantId(dto.getTenantId());
        return a;
    }
}
