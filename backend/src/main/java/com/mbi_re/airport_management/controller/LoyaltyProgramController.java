package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.LoyaltyProgramDTO;
import com.mbi_re.airport_management.model.LoyaltyProgram;
import com.mbi_re.airport_management.service.LoyaltyProgramService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loyalty")
@CrossOrigin
public class LoyaltyProgramController {

    private final LoyaltyProgramService service;

    public LoyaltyProgramController(LoyaltyProgramService service) {
        this.service = service;
    }

    @PostMapping
    public LoyaltyProgram save(@RequestBody LoyaltyProgramDTO dto) {
        return service.saveLoyalty(dto);
    }

    @GetMapping
    public List<LoyaltyProgram> getAll() {
        return service.getAll();
    }
}

