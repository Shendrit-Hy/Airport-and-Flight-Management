package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.FlightStatusDTO;
import com.mbi_re.airport_management.model.FlightStatus;
import com.mbi_re.airport_management.repository.FlightStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightStatusService {

    @Autowired
    private FlightStatusRepository repository;

    public FlightStatusDTO getStatus(Long flightId) {
        String tenantId = TenantContext.getTenantId();
        FlightStatus fs = repository.findByIdAndTenantId(flightId, tenantId)
                .orElseThrow(() -> new RuntimeException("Flight status not found"));
        return toDto(fs);
    }

    public List<FlightStatusDTO> getLiveFlights() {
        String tenantId = TenantContext.getTenantId();
        return repository.findByStatusAndTenantId("ACTIVE", tenantId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    private FlightStatusDTO toDto(FlightStatus f) {
        FlightStatusDTO dto = new FlightStatusDTO();
        dto.setId(f.getId());
        dto.setFlightNumber(f.getFlightNumber());
        dto.setStatus(f.getStatus());
        dto.setTenantId(f.getTenantId());
        return dto;
    }
}
