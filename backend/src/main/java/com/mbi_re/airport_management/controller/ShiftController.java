package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.ShiftDTO;
import com.mbi_re.airport_management.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shifts")
public class ShiftController {

    @Autowired
    private ShiftService service;

    @PostMapping
    public ShiftDTO create(@RequestBody ShiftDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<ShiftDTO> getAll() {
        return service.getAll();
    }
}
