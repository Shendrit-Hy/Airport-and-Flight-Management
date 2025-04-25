package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.PassengerDTO;
import com.mbi_re.airport_management.model.Passenger;
import com.mbi_re.airport_management.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public Passenger create(PassengerDTO dto) {
        Passenger p = new Passenger();
        p.setFullName(dto.getFullName());
        p.setEmail(dto.getEmail());
        p.setPhone(dto.getPhone());
        p.setNationality(dto.getNationality());
        return passengerRepository.save(p);
    }

    public List<Passenger> getAll() {
        return passengerRepository.findAll();
    }

    public Optional<Passenger> getById(Long id) {
        return passengerRepository.findById(id);
    }

    public Passenger update(Long id, PassengerDTO dto) {
        Passenger p = passengerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        p.setFullName(dto.getFullName());
        p.setEmail(dto.getEmail());
        p.setPhone(dto.getPhone());
        p.setNationality(dto.getNationality());
        return passengerRepository.save(p);
    }

    public void delete(Long id) {
        if (!passengerRepository.existsById(id)) {
            throw new RuntimeException("Passenger not found");
        }
        passengerRepository.deleteById(id);
    }
}

