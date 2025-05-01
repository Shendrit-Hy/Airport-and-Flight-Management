package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.SecurityDTO;
import com.mbi_re.airport_management.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security")
public class SecurityController {

    @Autowired
    private SecurityService service;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public SecurityDTO create(@RequestBody SecurityDTO dto) {
        return service.create(dto);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<SecurityDTO> getAll() {
        return service.getAll();
    }
}
