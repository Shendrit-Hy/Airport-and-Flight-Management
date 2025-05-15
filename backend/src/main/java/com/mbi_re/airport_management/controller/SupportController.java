package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.SupportDTO;
import com.mbi_re.airport_management.model.SupportRequest;
import com.mbi_re.airport_management.service.SupportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SupportRequest> fileComplaint(@RequestBody SupportDTO request) {

        return ResponseEntity.ok(service.fileComplaint(request));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')") // vetem ADMIN mund ti shoh te gjitha
    public ResponseEntity<List<SupportRequest>> getAll() {
        return ResponseEntity.ok(service.getAllComplaints());
    }
}
