package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.EmergencyDTO;
import com.mbi_re.airport_management.model.Emergency;
import com.mbi_re.airport_management.repository.EmergencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmergencyService {

    @Autowired
    private EmergencyRepository repo;

    public EmergencyDTO create(EmergencyDTO dto) {
        Emergency e = new Emergency();
        e.setType(dto.getType());
        e.setLocation(dto.getLocation());
        e.setReportedAt(dto.getReportedAt());
        repo.save(e);
        dto.setId(e.getId());
        return dto;
    }

    public List<EmergencyDTO> getAll() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private EmergencyDTO toDTO(Emergency e) {
        EmergencyDTO dto = new EmergencyDTO();
        dto.setId(e.getId());
        dto.setType(e.getType());
        dto.setLocation(e.getLocation());
        dto.setReportedAt(e.getReportedAt());
        return dto;
    }
}
