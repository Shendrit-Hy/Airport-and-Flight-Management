package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.PassengerDTO;
import com.mbi_re.airport_management.model.Passenger;
import com.mbi_re.airport_management.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class responsible for passenger operations.
 */
@Service
@RequiredArgsConstructor
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    /**
     * Retrieves all passengers associated with the specified tenant.
     * Uses caching to improve performance by avoiding repeated database queries.
     *
     * @param tenantId the tenant identifier to filter passengers
     * @return a list of passengers belonging to the tenant
     */
    @Cacheable(value = "passengers", key = "#tenantId")
    public List<Passenger> getAllByTenantId(String tenantId) {
        return passengerRepository.findAllByTenantId(tenantId);
    }

    /**
     * Updates an existing passenger's details by ID and tenant ID.
     * Evicts the cache for the tenant after successful update to keep data consistent.
     *
     * @param id       the ID of the passenger to update
     * @param updated  the DTO containing updated passenger data
     * @param tenantId the tenant identifier for authorization
     * @return the updated Passenger entity
     * @throws RuntimeException if the passenger does not exist or tenant is unauthorized
     */
    @CacheEvict(value = "passengers", key = "#tenantId")
    public Passenger update(Long id, PassengerDTO updated, String tenantId) {
        return passengerRepository.findByIdAndTenantId(id, tenantId)
                .map(p -> {
                    p.setFullName(updated.getFullName());
                    p.setEmail(updated.getEmail());
                    p.setPhone(updated.getPhone());
                    p.setAge(updated.getAge());
                    return passengerRepository.save(p);
                })
                .orElseThrow(() -> new RuntimeException("Passenger not found or unauthorized"));
    }

    /**
     * Saves a new passenger record for a specific tenant.
     * Evicts the tenant's cache to ensure new passenger is included in future queries.
     *
     * @param dto the passenger DTO containing passenger data and tenant ID
     * @return the saved passenger DTO with generated ID and tenant info
     */
    @CacheEvict(value = "passengers", key = "#dto.tenantId")
    public PassengerDTO savePassenger(PassengerDTO dto) {
        Passenger passenger = toEntity(dto);
        Passenger saved = passengerRepository.save(passenger);
        return toDTO(saved);
    }

    /**
     * Deletes a passenger by ID and tenant ID.
     * Evicts the tenant's cache after deletion to maintain cache consistency.
     *
     * @param id       the ID of the passenger to delete
     * @param tenantId the tenant identifier for authorization
     * @throws RuntimeException if the passenger does not exist or tenant is unauthorized
     */
    @CacheEvict(value = "passengers", key = "#tenantId")
    public void deleteById(Long id, String tenantId) {
        Optional<Passenger> passenger = passengerRepository.findByIdAndTenantId(id, tenantId);
        if (passenger.isPresent()) {
            passengerRepository.deleteByIdAndTenantId(id, tenantId);
        } else {
            throw new RuntimeException("Passenger not found or unauthorized");
        }
    }

    /**
     * Converts a Passenger entity to a PassengerDTO.
     *
     * @param passenger the Passenger entity to convert
     * @return the corresponding PassengerDTO object
     */
    private PassengerDTO toDTO(Passenger passenger) {
        PassengerDTO dto = new PassengerDTO();
        dto.setId(passenger.getId());
        dto.setFullName(passenger.getFullName());
        dto.setEmail(passenger.getEmail());
        dto.setPhone(passenger.getPhone());
        dto.setAge(passenger.getAge());
        dto.setTenantId(passenger.getTenantId());
        return dto;
    }

    /**
     * Converts a PassengerDTO to a Passenger entity.
     *
     * @param dto the PassengerDTO to convert
     * @return the corresponding Passenger entity
     */
    private Passenger toEntity(PassengerDTO dto) {
        Passenger passenger = new Passenger();
        passenger.setFullName(dto.getFullName());
        passenger.setEmail(dto.getEmail());
        passenger.setPhone(dto.getPhone());
        passenger.setAge(dto.getAge());
        passenger.setTenantId(dto.getTenantId());
        return passenger;
    }
}
