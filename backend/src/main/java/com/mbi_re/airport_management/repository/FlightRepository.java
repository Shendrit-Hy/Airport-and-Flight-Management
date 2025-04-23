package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
