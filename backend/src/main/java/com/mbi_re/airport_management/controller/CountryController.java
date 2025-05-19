package com.mbi_re.airport_management.controller;

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
    public List<CountryDTO> getAllCountries() {
        return countryService.getAllCountries();
    }

    @PostMapping
    public CountryDTO createCountry(@RequestBody CountryDTO dto) {
        return countryService.createCountry(dto);
    }
}
