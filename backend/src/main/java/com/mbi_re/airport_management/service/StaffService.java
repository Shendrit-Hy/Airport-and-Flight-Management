package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.StaffDTO;
import com.mbi_re.airport_management.model.Staff;
import com.mbi_re.airport_management.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    /**
     * Creates a new staff member.
     *
     * @param dto the staff data transfer object
     * @return the created StaffDTO
     */
    @CacheEvict(value = "staffList", key = "#dto.tenantId")
    public StaffDTO create(StaffDTO dto) {
        Staff staff = toEntity(dto);
        staffRepository.save(staff);
        return toDTO(staff);
    }

    /**
     * Retrieves all staff members for the given tenant.
     *
     * @param tenantId the tenant ID
     * @return a list of StaffDTOs
     */
    @Cacheable(value = "staffList", key = "#tenantId")
    public List<StaffDTO> getAll(String tenantId) {
        return staffRepository.findByTenantId(tenantId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a staff member by ID and tenant ID.
     *
     * @param id the staff ID
     * @return the matching StaffDTO
     */
    @Cacheable(value = "staff", key = "#id")
    public StaffDTO getByIdAndTenantId(Long id) {
        Staff staff = staffRepository.findByIdAndTenantId(id, TenantContext.getTenantId())
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        return toDTO(staff);
    }

    /**
     * Updates an existing staff member.
     *
     * @param id the staff ID
     * @param dto the updated staff data
     * @return the updated StaffDTO
     */
    @CachePut(value = "staff", key = "#id")
    @CacheEvict(value = "staffList", key = "#dto.tenantId")
    public StaffDTO update(Long id, StaffDTO dto) {
        Staff staff = staffRepository.findByIdAndTenantId(id, TenantContext.getTenantId())
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        staff.setName(dto.getName());
        staff.setRole(dto.getRole());
        staff.setEmail(dto.getEmail());
        staff.setShiftStart(dto.getShiftStart());
        staff.setShiftEnd(dto.getShiftEnd());
        staffRepository.save(staff);
        return toDTO(staff);
    }

    /**
     * Deletes a staff member by ID.
     *
     * @param id the staff ID
     */
    @CacheEvict(value = {"staff", "staffList"}, allEntries = true)
    public void delete(Long id) {
        Staff staff = staffRepository.findByIdAndTenantId(id, TenantContext.getTenantId())
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        staffRepository.delete(staff);
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