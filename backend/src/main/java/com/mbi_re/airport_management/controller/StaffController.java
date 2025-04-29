package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.StaffDTO;
import com.mbi_re.airport_management.service.StaffService;
import com.mbi_re.airport_management.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService service;

    @PostMapping
    public StaffDTO create(@RequestBody StaffDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<StaffDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public StaffDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public StaffDTO update(@PathVariable Long id, @RequestBody StaffDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
