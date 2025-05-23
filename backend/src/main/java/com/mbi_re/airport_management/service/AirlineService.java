package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.AirlineDTO;
import com.mbi_re.airport_management.model.Airline;
import com.mbi_re.airport_management.repository.AirlineRepository;
import jakarta.persistence.EntityNotFoundException;
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
     * Retrieves all airlines associated with the specified tenant.
     * Results are cached by tenantId to optimize repeated queries.
     *
     * @param tenantId the identifier of the tenant whose airlines to retrieve
     * @return a list of AirlineDTOs representing airlines of the tenant
     */
    @Cacheable(value = "airlines", key = "#tenantId")
    public List<AirlineDTO> getAllAirlines(String tenantId) {
        return airlineRepository.findByTenantId(tenantId).stream()
                .map(a -> new AirlineDTO(a.getId(), a.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Creates and persists a new airline for the given tenant.
     * Throws a RuntimeException if an airline with the same name already exists for the tenant.
     *
     * @param dto the data transfer object containing airline details
     * @param tenantId the identifier of the tenant for whom the airline is created
     * @return the created AirlineDTO including the generated airline ID
     * @throws RuntimeException if airline with the same name already exists for the tenant
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

    /**
     * Deletes an existing airline by its ID for the specified tenant.
     * Throws EntityNotFoundException if the airline does not exist.
     *
     * @param airlineId the ID of the airline to delete
     * @param tenantId the identifier of the tenant that owns the airline
     * @throws EntityNotFoundException if the airline is not found for the tenant
     */
    public void deleteAirline(Long airlineId, String tenantId) {
        Airline existing = airlineRepository.findByIdAndTenantId(airlineId, tenantId)
                .orElseThrow(() -> new EntityNotFoundException("Airline not found"));

        airlineRepository.delete(existing);
    }

}
