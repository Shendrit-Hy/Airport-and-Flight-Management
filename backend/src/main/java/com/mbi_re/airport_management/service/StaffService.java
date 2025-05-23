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
import java.util.stream.Collectors;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    /**
     * Creates a new staff member.
     * Evicts the cached staff list for the tenant to keep cache consistent.
     *
     * @param dto the staff data transfer object containing new staff info
     * @return the created StaffDTO representing the saved staff member
     */
    @CacheEvict(value = "staffList", key = "#dto.tenantId")
    public StaffDTO create(StaffDTO dto) {
        Staff staff = toEntity(dto);
        staffRepository.save(staff);
        return toDTO(staff);
    }

    /**
     * Retrieves all staff members associated with a tenant.
     * Results are cached to optimize performance.
     *
     * @param tenantId the tenant ID whose staff should be fetched
     * @return list of StaffDTOs representing staff for the tenant
     */
    @Cacheable(value = "staffList", key = "#tenantId")
    public List<StaffDTO> getAll(String tenantId) {
        return staffRepository.findByTenantId(tenantId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a staff member by their ID and current tenant ID.
     * Uses caching for faster repeated access.
     *
     * @param id the ID of the staff member to retrieve
     * @return StaffDTO of the matching staff member
     * @throws RuntimeException if staff not found for the current tenant
     */
    @Cacheable(value = "staff", key = "#id")
    public StaffDTO getByIdAndTenantId(Long id) {
        Staff staff = staffRepository.findByIdAndTenantId(id, TenantContext.getTenantId())
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        return toDTO(staff);
    }

    /**
     * Updates an existing staff member by their ID.
     * Updates cache entries for the staff and evicts staff list cache for the tenant.
     *
     * @param id  the ID of the staff member to update
     * @param dto the updated staff information
     * @return the updated StaffDTO after saving changes
     * @throws RuntimeException if staff not found for the current tenant
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
     * Deletes a staff member by their ID.
     * Evicts all cache entries for individual staff and staff list to maintain cache integrity.
     *
     * @param id the ID of the staff member to delete
     * @throws RuntimeException if staff not found for the current tenant
     */
    @CacheEvict(value = {"staff", "staffList"}, allEntries = true)
    public void delete(Long id) {
        Staff staff = staffRepository.findByIdAndTenantId(id, TenantContext.getTenantId())
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        staffRepository.delete(staff);
    }

    /**
     * Converts a Staff entity to a StaffDTO.
     *
     * @param staff the Staff entity
     * @return the corresponding StaffDTO
     */
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

    /**
     * Converts a StaffDTO to a Staff entity.
     *
     * @param dto the StaffDTO object
     * @return the corresponding Staff entity
     */
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
