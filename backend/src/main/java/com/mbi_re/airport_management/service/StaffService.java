package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.StaffDTO;
import com.mbi_re.airport_management.model.Staff;
import com.mbi_re.airport_management.repository.StaffRepository;
import com.mbi_re.airport_management.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffService {

    @Autowired
    private StaffRepository repository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    private String getCurrentTenantId() {
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return tokenProvider.getTenantIdFromJWT(token);
    }

    public StaffDTO create(StaffDTO dto) {
        Staff staff = toEntity(dto);
        staff.setTenantId(getCurrentTenantId()); // ➡️ Set tenantId automatically
        repository.save(staff);
        return toDTO(staff);
    }

    public List<StaffDTO> getAll() {
        String tenantId = getCurrentTenantId();
        return repository.findAll().stream()
                .filter(staff -> tenantId.equals(staff.getTenantId()))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public StaffDTO getById(Long id) {
        Staff staff = repository.findById(id).orElseThrow();
        if (!staff.getTenantId().equals(getCurrentTenantId())) {
            throw new RuntimeException("Access denied");
        }
        return toDTO(staff);
    }

    public StaffDTO update(Long id, StaffDTO dto) {
        Staff staff = repository.findById(id).orElseThrow();
        if (!staff.getTenantId().equals(getCurrentTenantId())) {
            throw new RuntimeException("Access denied");
        }
        staff.setName(dto.getName());
        staff.setRole(dto.getRole());
        staff.setEmail(dto.getEmail());
        repository.save(staff);
        return toDTO(staff);
    }

    public void delete(Long id) {
        Staff staff = repository.findById(id).orElseThrow();
        if (!staff.getTenantId().equals(getCurrentTenantId())) {
            throw new RuntimeException("Access denied");
        }
        repository.delete(staff);
    }

    private StaffDTO toDTO(Staff staff) {
        StaffDTO dto = new StaffDTO();
        dto.setId(staff.getId());
        dto.setName(staff.getName());
        dto.setRole(staff.getRole());
        dto.setEmail(staff.getEmail());
        return dto;
    }

    private Staff toEntity(StaffDTO dto) {
        Staff staff = new Staff();
        staff.setName(dto.getName());
        staff.setRole(dto.getRole());
        staff.setEmail(dto.getEmail());
        return staff;
    }
}
