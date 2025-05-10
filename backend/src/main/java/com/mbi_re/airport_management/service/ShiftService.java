package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.ShiftDTO;
import com.mbi_re.airport_management.model.Shift;
import com.mbi_re.airport_management.repository.ShiftRepository;
import com.mbi_re.airport_management.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShiftService {

    @Autowired
    private ShiftRepository repo;

    @Autowired
    private JwtTokenProvider tokenProvider;

    private String getCurrentTenantId() {
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return tokenProvider.getTenantIdFromJWT(token);
    }

    public ShiftDTO create(ShiftDTO dto) {
        Shift shift = new Shift();
        shift.setShiftName(dto.getShiftName());
        shift.setStartTime(dto.getStartTime());
        shift.setEndTime(dto.getEndTime());
        shift.setTenantId(getCurrentTenantId());
        repo.save(shift);
        dto.setId(shift.getId());
        return dto;
    }

    public List<ShiftDTO> getAll() {
        String tenantId = getCurrentTenantId();
        return repo.findAll().stream()
                .filter(shift -> tenantId.equals(shift.getTenantId()))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ShiftDTO toDTO(Shift shift) {
        ShiftDTO dto = new ShiftDTO();
        dto.setId(shift.getId());
        dto.setShiftName(shift.getShiftName());
        dto.setStartTime(shift.getStartTime());
        dto.setEndTime(shift.getEndTime());
        return dto;
    }
}
