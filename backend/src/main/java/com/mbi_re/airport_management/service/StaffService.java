package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.StaffDTO;
import com.mbi_re.airport_management.model.Staff;
import com.mbi_re.airport_management.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    public StaffDTO create(StaffDTO dto) {
        Staff staff = toEntity(dto);
        staffRepository.save(staff);
        return toDTO(staff);
    }

    public List<StaffDTO> getAll(String tenantId) {
        List<Staff> staff = staffRepository.findByTenantId(tenantId);

        return staffRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public StaffDTO getByIdAndTenantId(Long id) {
        Staff staff = staffRepository.findByIdAndTenantId(id, TenantContext.getTenantId())
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        return toDTO(staff);
    }

    public StaffDTO update(Long id, StaffDTO dto) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        staff.setName(dto.getName());
        staff.setRole(dto.getRole());
        staff.setEmail(dto.getEmail());
        staff.setShiftStart(dto.getShiftStart());
        staff.setShiftEnd(dto.getShiftEnd());
        staffRepository.save(staff);
        return toDTO(staff);
    }

    public void delete(Long id) {
        staffRepository.deleteById(id);
    }

    private StaffDTO toDTO(Staff staff) {
        StaffDTO dto = new StaffDTO();
        dto.setId(staff.getId());
        dto.setName(staff.getName());
        dto.setRole(staff.getRole());
        dto.setEmail(staff.getEmail());
        dto.setShiftStart(staff.getShiftStart());
        dto.setShiftEnd(staff.getShiftEnd());
        dto.setTenantId(staff.getTenantId());
        return dto;
    }

    private Staff toEntity(StaffDTO dto) {
        Staff staff = new Staff();
        staff.setName(dto.getName());
        staff.setRole(dto.getRole());
        staff.setEmail(dto.getEmail());
        staff.setShiftStart(dto.getShiftStart());
        staff.setShiftEnd(dto.getShiftEnd());
        staff.setTenantId(dto.getTenantId());
        return staff;
    }
}