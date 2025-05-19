package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.PassengerDTO;
import com.mbi_re.airport_management.model.Passenger;
import com.mbi_re.airport_management.repository.PassengerRepository;
import org.springframework.stereotype.Service;

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
                    p.setNationality(updated.getNationality());
                    return passengerRepository.save(p);
                })
                .orElseThrow(() -> new RuntimeException("Passenger not found or unauthorized"));
    }

    public void deleteById(Long id) {
        passengerRepository.deleteById(id);
    }
}
