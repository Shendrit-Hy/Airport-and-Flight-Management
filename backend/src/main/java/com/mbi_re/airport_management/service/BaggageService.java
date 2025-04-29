package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.BaggageDTO;
import com.mbi_re.airport_management.model.Baggage;
import com.mbi_re.airport_management.repository.BaggageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BaggageService {

    @Autowired
    private BaggageRepository repository;


    public BaggageDTO create(BaggageDTO dto) {
        Baggage entity = new Baggage();
        entity.setBaggageTag(dto.getBaggageTag());
        entity.setAirportID(dto.getAirportID());
        entity.setStatus(dto.getStatus());
        entity.setDescription(dto.getDescription());
        entity.setLastUpdated(dto.getLastUpdated());

        Baggage saved = repository.save(entity);
        dto.setId(saved.getId());
        return dto;
    }


    public List<BaggageDTO> getAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<BaggageDTO> getById(Long id) {
        return repository.findById(id).map(this::toDTO);
    }

    public Optional<BaggageDTO> update(Long id, BaggageDTO dto) {
        Optional<Baggage> existingBaggage = repository.findById(id);
        if (existingBaggage.isPresent()) {
            Baggage entity = existingBaggage.get();
            entity.setBaggageTag(dto.getBaggageTag());
            entity.setAirportID(dto.getAirportID());
            entity.setStatus(dto.getStatus());
            entity.setDescription(dto.getDescription());
            entity.setLastUpdated(dto.getLastUpdated());
            Baggage updated = repository.save(entity);
            return Optional.of(toDTO(updated));
        }
        return Optional.empty();
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    private BaggageDTO toDTO(Baggage entity) {
        BaggageDTO dto = new BaggageDTO();
        dto.setId(entity.getId());
        dto.setBaggageTag(entity.getBaggageTag());
        dto.setAirportID(entity.getAirportID());
        dto.setStatus(entity.getStatus());
        dto.setDescription(entity.getDescription());
        dto.setLastUpdated(entity.getLastUpdated());
        return dto;
    }
}

