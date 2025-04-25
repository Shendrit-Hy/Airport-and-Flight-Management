package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.AnnouncementDTO;
import com.mbi_re.airport_management.model.Announcement;
import com.mbi_re.airport_management.service.AnnouncementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@CrossOrigin
public class AnnouncementController {

    private final AnnouncementService service;

    public AnnouncementController(AnnouncementService service) {
        this.service = service;
    }

    @PostMapping
    public Announcement createAnnouncement(@RequestBody AnnouncementDTO dto) {
        return service.createAnnouncement(dto);
    }

    @GetMapping
    public List<Announcement> getAll() {
        return service.getAllAnnouncements();
    }
}

