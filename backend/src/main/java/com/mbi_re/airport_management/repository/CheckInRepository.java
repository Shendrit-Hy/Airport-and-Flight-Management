package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    Optional<CheckIn> findByBookingId(String bookingId);
    List<CheckIn> findByBoardedFalse();
}

