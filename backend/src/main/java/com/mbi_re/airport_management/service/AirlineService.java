package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.AirlineDTO;
import com.mbi_re.airport_management.model.Airline;
import com.mbi_re.airport_management.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirlineService {

    @Autowired
    private AirlineRepository airlineRepository;

    /**
     * Retrieves all airlines for a specific tenant.
     *
     * @param tenantId the tenant identifier
     * @return list of airline DTOs
     */
    @Cacheable(value = "airlines", key = "#tenantId")
    public List<AirlineDTO> getAllAirlines(String tenantId) {
        return airlineRepository.findByTenantId(tenantId).stream()
                .map(a -> new AirlineDTO(a.getId(), a.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Creates a new airline for the specified tenant.
     *
     * @param dto      the airline data transfer object
     * @param tenantId the tenant identifier
     * @return the created airline as a DTO
     * @throws RuntimeException if the airline already exists for the tenant
     */
    public AirlineDTO createAirline(AirlineDTO dto, String tenantId) {
        if (airlineRepository.existsByNameAndTenantId(dto.getName(), tenantId)) {
            throw new RuntimeException("Airline already exists for this tenant!");
        }

        Airline airline = new Airline();
        airline.setName(dto.getName());
        airline.setTenantId(tenantId);

        Airline saved = airlineRepository.save(airline);
        return new AirlineDTO(saved.getId(), saved.getName());
    }
}
