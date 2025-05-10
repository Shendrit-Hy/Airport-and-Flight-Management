package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.SecurityDTO;
import com.mbi_re.airport_management.model.Security;
import com.mbi_re.airport_management.repository.SecurityRepository;
import com.mbi_re.airport_management.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecurityService {

    @Autowired
    private SecurityRepository repo;

    @Autowired
    private JwtTokenProvider tokenProvider;

    private String getCurrentTenantId() {
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return tokenProvider.getTenantIdFromJWT(token);
    }

    public SecurityDTO create(SecurityDTO dto) {
        Security sec = new Security();
        sec.setGuardName(dto.getGuardName());
        sec.setAssignedShift(dto.getAssignedShift());
        sec.setTenantId(getCurrentTenantId());
        repo.save(sec);
        dto.setId(sec.getId());
        return dto;
    }

    public List<SecurityDTO> getAll() {
        String tenantId = getCurrentTenantId();
        return repo.findAll().stream()
                .filter(sec -> tenantId.equals(sec.getTenantId()))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private SecurityDTO toDTO(Security sec) {
        SecurityDTO dto = new SecurityDTO();
        dto.setId(sec.getId());
        dto.setGuardName(sec.getGuardName());
        dto.setAssignedShift(sec.getAssignedShift());
        return dto;
    }
}
