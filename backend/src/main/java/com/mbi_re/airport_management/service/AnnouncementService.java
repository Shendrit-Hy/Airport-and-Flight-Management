package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.AnnouncementDTO;
import com.mbi_re.airport_management.model.Announcement;
import com.mbi_re.airport_management.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AnnouncementRepository announcementRepository;

    /**
     * Retrieves all announcements associated with the specified tenant.
     * This method leverages caching for improved performance, especially for
     * public or unauthenticated access.
     *
     * @param tenantId the identifier of the tenant whose announcements are retrieved
     * @return list of AnnouncementDTOs belonging to the tenant
     */
    @Cacheable(value = "announcements", key = "#tenantId")
    public List<AnnouncementDTO> getAnnouncementsByTenant(String tenantId) {
        return announcementRepository.findByTenantId(tenantId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Saves a new announcement entity for a tenant.
     * Upon saving, the announcements cache for the tenant is evicted to
     * maintain consistency.
     *
     * @param dto the announcement data transfer object containing announcement details
     * @return the saved announcement as a DTO including any generated fields (e.g. timestamps)
     */
    @CacheEvict(value = "announcements", key = "#dto.tenantId")
    public AnnouncementDTO saveAnnouncement(AnnouncementDTO dto) {
        Announcement announcement = toEntity(dto);
        announcement.setCreatedAt(LocalDateTime.now());
        Announcement saved = announcementRepository.save(announcement);
        return toDTO(saved);
    }

    /**
     * Converts an Announcement entity to its corresponding DTO representation.
     *
     * @param announcement the Announcement entity to convert
     * @return the equivalent AnnouncementDTO
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
     * Converts an AnnouncementDTO into an Announcement entity.
     *
     * @param dto the AnnouncementDTO to convert
     * @return the equivalent Announcement entity
     */
    private Announcement toEntity(AnnouncementDTO dto) {
        Announcement a = new Announcement();
        a.setTitle(dto.getTitle());
        a.setMessage(dto.getMessage());
        a.setTenantId(dto.getTenantId());
        return a;
    }
}
