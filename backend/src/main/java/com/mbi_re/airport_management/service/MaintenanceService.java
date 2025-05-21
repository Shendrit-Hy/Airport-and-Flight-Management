package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.MaintenanceDTO;
import com.mbi_re.airport_management.model.Maintenance;
import com.mbi_re.airport_management.repository.MaintenanceRepository;
import com.mbi_re.airport_management.config.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing maintenance records with tenant isolation.
 */
@Service
public class MaintenanceService {

    @Autowired
    private MaintenanceRepository repository;

    /**
     * Creates a new maintenance record for the current tenant.
     *
     * @param dto the maintenance request data
     * @return the saved maintenance DTO
     */
    @CacheEvict(value = "maintenance", allEntries = true)
    public MaintenanceDTO create(MaintenanceDTO dto) {
        Maintenance entity = new Maintenance();
        entity.setAirportID(dto.getAirportCode());
        entity.setLocation(dto.getLocation());
        entity.setIssueType(dto.getIssueType());
        entity.setReportedBy(dto.getReportedBy());
        entity.setPriority(dto.getPriority());
        entity.setStatus(dto.getStatus());
        entity.setDescription(dto.getDescription());
        entity.setReportedAt(LocalDateTime.now());
        entity.setTenantId(dto.getTenantId());

        Maintenance saved = repository.save(entity);
        return toDTO(saved);
    }

    /**
     * Retrieves all maintenance records for the current tenant.
     *
     * @return list of maintenance DTOs
     */
    @Cacheable(value = "maintenance")
    public List<MaintenanceDTO> getAll() {
        String tenantId = TenantContext.getTenantId();
        return repository.findAllByTenantId(tenantId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves maintenance records filtered by airport code.
     *
     * @param airportCode the airport code
     * @return list of matching DTOs
     */
    @Cacheable(value = "maintenance", key = "#airportCode")
    public List<MaintenanceDTO> getByAirportCode(String airportCode) {
        String tenantId = TenantContext.getTenantId();
        return repository.findByAirportIDAndTenantId(airportCode, tenantId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves maintenance records filtered by status.
     *
     * @param status the maintenance status
     * @return list of matching DTOs
     */
    @Cacheable(value = "maintenance", key = "#status")
    public List<MaintenanceDTO> getByStatus(String status) {
        String tenantId = TenantContext.getTenantId();
        return repository.findByStatusAndTenantId(status, tenantId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves maintenance records filtered by both airport code and status.
     *
     * @param airportCode the airport code
     * @param status      the maintenance status
     * @return list of matching DTOs
     */
    @Cacheable(value = "maintenance", key = "#airportCode + '-' + #status")
    public List<MaintenanceDTO> getByAirportCodeAndStatus(String airportCode, String status) {
        String tenantId = TenantContext.getTenantId();
        return repository.findByAirportIDAndStatusAndTenantId(airportCode, status, tenantId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a maintenance record by ID and tenant ID.
     *
     * @param id       the record ID
     * @param tenantId the tenant identifier
     */
    @CacheEvict(value = "maintenance", allEntries = true)
    public void delete(Long id, String tenantId) {
        Maintenance m = repository.findByIdAndTenantId(id, tenantId);
        if (m == null) {
            throw new RuntimeException("Maintenance record not found for tenant");
        }
        repository.deleteById(id);
    }

    /**
     * Converts a Maintenance entity to a DTO.
     *
     * @param entity the maintenance entity
     * @return the DTO representation
     */
    private MaintenanceDTO toDTO(Maintenance entity) {
        MaintenanceDTO dto = new MaintenanceDTO();
        dto.setId(entity.getId());
        dto.setAirportCode(entity.getAirportID());
        dto.setLocation(entity.getLocation());
        dto.setIssueType(entity.getIssueType());
        dto.setReportedBy(entity.getReportedBy());
        dto.setPriority(entity.getPriority());
        dto.setStatus(entity.getStatus());
        dto.setDescription(entity.getDescription());
        dto.setReportedAt(entity.getReportedAt());
        return dto;
    }
}
