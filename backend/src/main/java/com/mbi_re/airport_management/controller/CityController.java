package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.CityDTO;
import com.mbi_re.airport_management.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public List<CityDTO> getAllCities() {
        return cityService.getAllCities();
    }

    @PostMapping
    public CityDTO createCity(@RequestBody CityDTO dto) {
        return cityService.createCity(dto);
    }

    @GetMapping("/country/{countryId}")
    public List<CityDTO> getCitiesByCountry(@PathVariable Long countryId) {
        return cityService.getCitiesByCountry(countryId);
    }
}
