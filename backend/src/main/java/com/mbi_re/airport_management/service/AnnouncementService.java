package com.mbi_re.airport_management.service;


import com.mbi_re.airport_management.dto.AnnouncementDTO;
import com.mbi_re.airport_management.model.Announcement;
import com.mbi_re.airport_management.repository.AnnouncementRepository;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Builder
@Service
public class AnnouncementService {

    private final AnnouncementRepository repository;

    public AnnouncementService(AnnouncementRepository repository) {
        this.repository = repository;
    }

    public Announcement createAnnouncement(AnnouncementDTO dto) {
        Announcement announcement = Announcement.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .build();
        return repository.save(announcement);
    }

    public List<Announcement> getAllAnnouncements() {
        return repository.findAll();
    }
}

