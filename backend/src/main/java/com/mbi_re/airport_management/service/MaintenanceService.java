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
     * <p>
     * This method evicts all entries in the "maintenance" cache to keep data consistent.
     *
     * @param dto the maintenance request data transfer object containing maintenance details
     * @return the saved maintenance record as a DTO
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
     * <p>
     * Results are cached under the "maintenance" cache to optimize repeated calls.
     *
     * @return a list of maintenance record DTOs belonging to the current tenant
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
     * Retrieves maintenance records filtered by airport code for the current tenant.
     * <p>
     * Results are cached with the airport code as cache key.
     *
     * @param airportCode the airport code to filter maintenance records
     * @return a list of matching maintenance record DTOs
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
     * Retrieves maintenance records filtered by status for the current tenant.
     * <p>
     * Results are cached with the status as cache key.
     *
     * @param status the status to filter maintenance records
     * @return a list of matching maintenance record DTOs
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
     * Retrieves maintenance records filtered by both airport code and status for the current tenant.
     * <p>
     * Results are cached with a combined key of airportCode-status.
     *
     * @param airportCode the airport code to filter maintenance records
     * @param status      the status to filter maintenance records
     * @return a list of matching maintenance record DTOs
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
     * Deletes a maintenance record by ID for the given tenant.
     * <p>
     * This method evicts all entries in the "maintenance" cache.
     * Throws a {@link RuntimeException} if no matching record is found.
     *
     * @param id       the ID of the maintenance record to delete
     * @param tenantId the tenant ID to scope the deletion
     * @throws RuntimeException if maintenance record is not found for the tenant
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
     * Converts a {@link Maintenance} entity into a {@link MaintenanceDTO}.
     *
     * @param entity the maintenance entity to convert
     * @return the corresponding maintenance DTO
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
