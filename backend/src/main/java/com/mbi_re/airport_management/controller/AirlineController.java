package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.AirlineDTO;
import com.mbi_re.airport_management.service.AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
public class AirlineController {

    @Autowired
    private AirlineService airlineService;

    @GetMapping
    public List<AirlineDTO> getAllAirlines() {
        return airlineService.getAllAirlines();
    }

    @PostMapping
    public AirlineDTO createAirline(@RequestBody AirlineDTO dto) {
        return airlineService.createAirline(dto);
    }
}
