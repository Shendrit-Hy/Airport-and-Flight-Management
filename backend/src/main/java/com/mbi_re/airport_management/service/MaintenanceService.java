package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.MaintenanceDTO;
import com.mbi_re.airport_management.model.Maintenance;
import com.mbi_re.airport_management.repository.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceService {

    @Autowired
    private MaintenanceRepository repository;

    public MaintenanceDTO create(MaintenanceDTO dto) {
        Maintenance entity = new Maintenance();
        entity.setAirportID(dto.getAirportCode());
        entity.setLocation(dto.getLocation());
        entity.setIssueType(dto.getIssueType());
        entity.setReportedBy(dto.getReportedBy());
        entity.setPriority(dto.getPriority());
        entity.setStatus(dto.getStatus());
        entity.setDescription(dto.getDescription());
        entity.setReportedAt(dto.getReportedAt());

        Maintenance saved = repository.save(entity);
        dto.setId(saved.getId());
        return dto;
    }

    public List<MaintenanceDTO> getAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<MaintenanceDTO> getByAirportCode(String airportCode) {
        return repository.findByAirportCode(airportCode).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<MaintenanceDTO> getByStatus(String status) {
        return repository.findByStatus(status).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<MaintenanceDTO> getByAirportCodeAndStatus(String airportCode, String status) {
        return repository.findByAirportCodeAndStatus(airportCode, status).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private MaintenanceDTO toDTO(Maintenance entity) {
        MaintenanceDTO dto = new MaintenanceDTO();
        dto.setId(entity.getId());
        dto.setAirportCode(entity.getAirportID());
        dto.setLocation(entity.getLocation());
        dto.setIssueType(entity.getIssueType());
        dto.setReportedBy(entity.getReportedBy());
        dto.setPriority(entity.getPriority());
        dto.setStatus(entity.getStatus());
        dto.setDescription(entity.getDescription());
        dto.setReportedAt(entity.getReportedAt());
        return dto;
    }
}
