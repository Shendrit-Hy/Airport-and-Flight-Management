package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.StaffDTO;
import com.mbi_re.airport_management.model.Staff;
import com.mbi_re.airport_management.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffService {

    @Autowired
    private StaffRepository repository;

    public StaffDTO create(StaffDTO dto) {
        Staff staff = toEntity(dto);
        repository.save(staff);
        return toDTO(staff);
    }

    public List<StaffDTO> getAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public StaffDTO getById(Long id) {
        Staff staff = repository.findById(id).orElseThrow();
        return toDTO(staff);
    }

    public StaffDTO update(Long id, StaffDTO dto) {
        Staff staff = repository.findById(id).orElseThrow();
        staff.setName(dto.getName());
        staff.setRole(dto.getRole());
        staff.setEmail(dto.getEmail());
        repository.save(staff);
        return toDTO(staff);
    }

    public void delete(Long id) {
        repository.deleteById(id);
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
