package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.StaffDTO;
import com.mbi_re.airport_management.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService service;
    private void validateTenant(String tenantHeader) {
        String contextTenant = TenantContext.getTenantId();
        if (tenantHeader == null || !tenantHeader.equals(contextTenant)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid tenant ID");
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public StaffDTO create(@RequestBody StaffDTO dto,
                           @RequestHeader("X-Tenant-ID") String tenantId) {
        validateTenant(tenantId);
        return service.create(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<StaffDTO> getAll(@RequestHeader("X-Tenant-ID") String tenantId) {
        validateTenant(tenantId);
        return service.getAll(tenantId);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public StaffDTO getById(@PathVariable Long id,
                            @RequestHeader("X-Tenant-ID") String tenantId) {
        validateTenant(tenantId);
        return service.getByIdAndTenantId(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public StaffDTO update(@PathVariable Long id,
                           @RequestBody StaffDTO dto,
                           @RequestHeader("X-Tenant-ID") String tenantId) {
        validateTenant(tenantId);
        return service.update(id, dto);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @RequestHeader("X-Tenant-ID") String tenantId) {
        validateTenant(tenantId);
        service.delete(id);
    }
}
