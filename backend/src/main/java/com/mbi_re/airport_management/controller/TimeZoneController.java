package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.TimeZoneDTO;
import com.mbi_re.airport_management.service.TimeZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timezones")
public class TimeZoneController {

    @Autowired
    private TimeZoneService timeZoneService;

    public TimeZoneController(TimeZoneService timeZoneService) {
        this.timeZoneService = timeZoneService;
    }

    @GetMapping
    public ResponseEntity<List<TimeZoneDTO>> getAllTimeZones() {
        return ResponseEntity.ok(timeZoneService.getAllTimeZones());
    }
}

