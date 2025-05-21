package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.CountryDTO;
import com.mbi_re.airport_management.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping
<<<<<<< Updated upstream
    public List<CountryDTO> getAllCountries() {
        return countryService.getAllCountries();
=======
    @Cacheable(value = "countries", key = "#tenantId")
    public ResponseEntity<List<CountryDTO>> getAllCountries(
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID from header", required = true) String tenantId) {

        TenantUtil.validateTenant(tenantId);
        System.out.println(TenantContext.getTenantId());
        return ResponseEntity.ok(countryService.getAllCountries());
>>>>>>> Stashed changes
    }

    @PostMapping
    public CountryDTO createCountry(@RequestBody CountryDTO dto) {
        return countryService.createCountry(dto);
    }
}
