package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.AirlineDTO;
import com.mbi_re.airport_management.model.Airline;
import com.mbi_re.airport_management.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirlineService {

    @Autowired
    private AirlineRepository airlineRepository;

    public List<AirlineDTO> getAllAirlines() {
        return airlineRepository.findAll().stream()
                .map(a -> new AirlineDTO(a.getId(), a.getName()))
                .collect(Collectors.toList());
    }

    public AirlineDTO createAirline(AirlineDTO dto) {
        if (airlineRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Airline already exists!");
        }

        Airline airline = new Airline();
        airline.setName(dto.getName());

        Airline saved = airlineRepository.save(airline);
        return new AirlineDTO(saved.getId(), saved.getName());
    }
}
