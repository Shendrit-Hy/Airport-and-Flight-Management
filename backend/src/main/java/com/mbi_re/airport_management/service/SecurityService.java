package com.mbi_re.airport_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.mbi_re.airport_management.dto.SecurityDTO;
import com.mbi_re.airport_management.model.Security;
import com.mbi_re.airport_management.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecurityService {

    @Autowired
    private SecurityRepository repo;

    public SecurityDTO create(SecurityDTO dto) {
        Security sec = new Security();
        sec.setGuardName(dto.getGuardName());
        sec.setAssignedShift(dto.getAssignedShift());
        repo.save(sec);
        dto.setId(sec.getId());
        return dto;
    }

    public List<SecurityDTO> getAll() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private SecurityDTO toDTO(Security sec) {
        SecurityDTO dto = new SecurityDTO();
        dto.setId(sec.getId());
        dto.setGuardName(sec.getGuardName());
        dto.setAssignedShift(sec.getAssignedShift());
        return dto;
    }
}
