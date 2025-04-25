package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.LoyaltyProgramDTO;
import com.mbi_re.airport_management.model.LoyaltyProgram;
import com.mbi_re.airport_management.repository.LoyaltyProgramRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoyaltyProgramService {

    private final LoyaltyProgramRepository repository;

    public LoyaltyProgramService(LoyaltyProgramRepository repository) {
        this.repository = repository;
    }

    public LoyaltyProgram saveLoyalty(LoyaltyProgramDTO dto) {
        LoyaltyProgram program = LoyaltyProgram.builder()
                .passengerName(dto.getPassengerName())
                .loyaltyId(dto.getLoyaltyId())
                .points(dto.getPoints())
                .tier(dto.getTier())
                .build();

        return repository.save(program);
    }

    public List<LoyaltyProgram> getAll() {
        return repository.findAll();
    }
}

