package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.PassengerDTO;
import com.mbi_re.airport_management.dto.PaymentDTO;
import com.mbi_re.airport_management.model.Passenger;
import com.mbi_re.airport_management.model.Payment;
import com.mbi_re.airport_management.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public List<Passenger> getAllByTenantId(String tenantId) {
        return passengerRepository.findAllByTenantId(tenantId);
    }

    public Passenger update(Long id, PassengerDTO updated, String tenantId) {
        return passengerRepository.findById(id)
                .filter(p -> p.getTenantId().equals(tenantId))
                .map(p -> {
                    p.setFullName(updated.getFullName());
                    p.setEmail(updated.getEmail());
                    p.setPhone(updated.getPhone());
                    p.setAge(updated.getAge());
                    return passengerRepository.save(p);
                })
                .orElseThrow(() -> new RuntimeException("Passenger not found or unauthorized"));
    }

    public PassengerDTO savePassenger(PassengerDTO dto) {
        Passenger passenger = toEntity(dto);
        passenger.setTenantId(dto.getTenantId());
        Passenger saved = passengerRepository.save(passenger);
        return toDTO(saved);
    }

    private PassengerDTO toDTO(Passenger passenger) {
        PassengerDTO dto = new PassengerDTO();
        dto.setId(passenger.getId());
        dto.setFullName(passenger.getFullName());
        dto.setEmail(passenger.getEmail());
        dto.setPhone(passenger.getPhone());
        dto.setAge(Long.valueOf(passenger.getAge()));
        dto.setTenantId(passenger.getTenantId());
        return dto;
    }

    private Passenger toEntity(PassengerDTO dto) {
        Passenger passenger = new Passenger();
        passenger.setFullName(dto.getFullName());
        passenger.setEmail(dto.getEmail());
        passenger.setPhone(dto.getPhone());
        passenger.setAge(Long.valueOf(dto.getAge()));
        passenger.setTenantId(dto.getTenantId());
        return passenger;
    }


    public void deleteById(Long id) {
        passengerRepository.deleteById(id);
    }
}
