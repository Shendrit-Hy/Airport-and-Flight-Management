package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.PassengerDTO;
import com.mbi_re.airport_management.model.Passenger;
import com.mbi_re.airport_management.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
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

    private final PassengerRepository passengerRepository;

    /**
     * Retrieves all passengers for a given tenant.
     * Uses caching to avoid repeated lookups.
     *
     * @param tenantId the tenant identifier
     * @return list of passengers for the tenant
     */
    @Cacheable(value = "passengers", key = "#tenantId")
    public List<Passenger> getAllByTenantId(String tenantId) {
        return passengerRepository.findAllByTenantId(tenantId);
    }

    /**
     * Updates a passenger by ID and tenant ID.
     * Clears the cache for the tenant to keep data consistent.
     *
     * @param id       the passenger ID
     * @param updated  the updated passenger DTO
     * @param tenantId the tenant identifier
     * @return the updated passenger
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
     * Saves a new passenger for a specific tenant.
     * Clears the cache after creation to ensure the new passenger is visible.
     *
     * @param dto the passenger DTO
     * @return the saved passenger DTO
     */
    @CacheEvict(value = "passengers", key = "#dto.tenantId")
    public PassengerDTO savePassenger(PassengerDTO dto) {
        Passenger passenger = toEntity(dto);
        Passenger saved = passengerRepository.save(passenger);
        return toDTO(saved);
    }

    /**
     * Deletes a passenger by ID and tenant ID.
     * Clears the cache for the tenant.
     *
     * @param id       the passenger ID
     * @param tenantId the tenant identifier
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
     * Converts a Passenger entity to a DTO.
     *
     * @param passenger the Passenger entity
     * @return the corresponding PassengerDTO
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
     * @param dto the PassengerDTO
     * @return the Passenger entity
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
