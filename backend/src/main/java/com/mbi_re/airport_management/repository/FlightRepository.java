package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByFlightDateGreaterThanEqual(LocalDate date);
}
