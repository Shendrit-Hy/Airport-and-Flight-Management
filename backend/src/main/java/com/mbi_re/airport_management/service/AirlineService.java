package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.AirlineDTO;
import com.mbi_re.airport_management.model.Airline;
import com.mbi_re.airport_management.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AirlineService {

    @Autowired
    private AirlineRepository repository;

    public AirlineDTO create(AirlineDTO dto) {
        Airline entity = toEntity(dto);
        Airline saved = repository.save(entity);
        dto.setId(saved.getId());
        return dto;
    }

    public List<AirlineDTO> getAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<AirlineDTO> getById(Long id) {
        return repository.findById(id).map(this::toDTO);
    }

    public Optional<AirlineDTO> update(Long id, AirlineDTO dto) {
        return repository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setIataCode(dto.getIataCode());
            existing.setCountry(dto.getCountry());
            existing.setHubAirport(dto.getHubAirport());
            return toDTO(repository.save(existing));
        });
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    private AirlineDTO toDTO(Airline entity) {
        AirlineDTO dto = new AirlineDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setIataCode(entity.getIataCode());
        dto.setCountry(entity.getCountry());
        dto.setHubAirport(entity.getHubAirport());
        return dto;
    }

    private Airline toEntity(AirlineDTO dto) {
        Airline entity = new Airline();
        entity.setName(dto.getName());
        entity.setIataCode(dto.getIataCode());
        entity.setCountry(dto.getCountry());
        entity.setHubAirport(dto.getHubAirport());
        return entity;
    }
}

