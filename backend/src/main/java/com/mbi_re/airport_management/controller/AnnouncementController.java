package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.AnnouncementDTO;
import com.mbi_re.airport_management.model.Announcement;
import com.mbi_re.airport_management.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping
    public List<AnnouncementDTO> getAnnouncements(@RequestHeader("X-Tenant-ID") String tenantId) {
        return announcementService.getAnnouncementsByTenant(tenantId);
    }

    @PostMapping
    public AnnouncementDTO createAnnouncement(@RequestBody AnnouncementDTO dto,
                                              @RequestHeader("X-Tenant-ID") String tenantId) {
        dto.setTenantId(tenantId);
        return announcementService.saveAnnouncement(dto);
    }
}
