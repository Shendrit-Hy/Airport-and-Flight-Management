package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.StaffDTO;
import com.mbi_re.airport_management.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public StaffDTO create(@RequestBody StaffDTO dto) {
        return staffService.create(dto);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<StaffDTO> getAll() {
        return staffService.getAll();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public StaffDTO getById(@PathVariable Long id) {
        return staffService.getById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public StaffDTO update(@PathVariable Long id, @RequestBody StaffDTO dto) {
        return staffService.update(id, dto);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        staffService.delete(id);
    }
}
