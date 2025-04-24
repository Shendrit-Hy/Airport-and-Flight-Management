package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}

