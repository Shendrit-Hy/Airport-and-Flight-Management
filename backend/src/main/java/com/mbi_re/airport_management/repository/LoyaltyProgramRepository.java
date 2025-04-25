package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.LoyaltyProgram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoyaltyProgramRepository extends JpaRepository<LoyaltyProgram, Long> {
}

