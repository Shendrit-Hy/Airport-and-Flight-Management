package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineRepository extends JpaRepository<Airline, Long> {
}
