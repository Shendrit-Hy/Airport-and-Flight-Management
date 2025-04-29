package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Emergency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyRepository extends JpaRepository<Emergency, Long> {}
