package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.model.SupportRequest;
import com.mbi_re.airport_management.service.SupportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support")
public class SupportController {
    private final SupportService service;

    public SupportController(SupportService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SupportRequest> fileComplaint(@RequestBody SupportRequest request) {

        return ResponseEntity.ok(service.fileComplaint(request));
    }

    @GetMapping
    public ResponseEntity<List<SupportRequest>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
