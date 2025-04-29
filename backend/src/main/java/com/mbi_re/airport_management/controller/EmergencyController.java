package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.EmergencyDTO;
import com.mbi_re.airport_management.service.EmergencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emergency")
public class EmergencyController {

    @Autowired
    private EmergencyService service;

    @PostMapping
    public EmergencyDTO create(@RequestBody EmergencyDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<EmergencyDTO> getAll() {
        return service.getAll();
    }
}
