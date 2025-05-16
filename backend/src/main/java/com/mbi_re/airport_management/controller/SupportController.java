package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.SupportDTO;
import com.mbi_re.airport_management.model.Support;
import com.mbi_re.airport_management.service.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support")
public class SupportController {

    @Autowired
    private SupportService service;

    public SupportController(SupportService service) {
        this.service = service;
    }

    // Allow anyone to submit a support ticket
    @PostMapping
    public ResponseEntity<Support> fileComplaint(
            @RequestBody SupportDTO request,
            @RequestHeader("X-Tenant-ID") String tenantId) {

        request.setTenantId(tenantId);
        return ResponseEntity.ok(service.fileComplaint(request));
    }

    // Only ADMINs can fetch all support tickets
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Support>> getAll() {
        return ResponseEntity.ok(service.getAllComplaints());
    }
}

