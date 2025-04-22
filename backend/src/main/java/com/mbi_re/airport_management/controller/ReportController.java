package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.ReportDTO;
import com.mbi_re.airport_management.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<ReportDTO> generateReport() {
        return ResponseEntity.ok(reportService.generateReport());
    }
}
