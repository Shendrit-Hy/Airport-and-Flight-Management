package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.ImmigrationDTO;
import com.mbi_re.airport_management.model.Immigration;
import com.mbi_re.airport_management.repository.ImmigrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImmigrationService {

    @Autowired
    private ImmigrationRepository repo;

    public List<ImmigrationDTO> getAll() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private ImmigrationDTO toDTO(Immigration i) {
        ImmigrationDTO dto = new ImmigrationDTO();
        dto.setId(i.getId());
        dto.setPersonName(i.getPersonName());
        dto.setNationality(i.getNationality());
        dto.setEntryDate(i.getEntryDate());
        return dto;
    }
}
