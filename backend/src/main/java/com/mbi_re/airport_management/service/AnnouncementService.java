package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.AnnouncementDTO;
import com.mbi_re.airport_management.model.Announcement;
import com.mbi_re.airport_management.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    public List<AnnouncementDTO> getAnnouncementsByTenant(String tenantId) {
        return announcementRepository.findByTenantId(tenantId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AnnouncementDTO saveAnnouncement(AnnouncementDTO dto) {
        Announcement announcement = toEntity(dto);
        announcement.setCreatedAt(LocalDateTime.now());
        Announcement saved = announcementRepository.save(announcement);
        return toDTO(saved);
    }

    private AnnouncementDTO toDTO(Announcement announcement) {
        AnnouncementDTO dto = new AnnouncementDTO();
        dto.setTitle(announcement.getTitle());
        dto.setMessage(announcement.getMessage());
        dto.setTenantId(announcement.getTenantId());
        dto.setCreatedAt(announcement.getCreatedAt());
        return dto;
    }

    private Announcement toEntity(AnnouncementDTO dto) {
        Announcement a = new Announcement();
        a.setTitle(dto.getTitle());
        a.setMessage(dto.getMessage());
        a.setTenantId(dto.getTenantId());
        return a;
    }
}

