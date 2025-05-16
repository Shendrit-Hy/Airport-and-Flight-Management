package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByTenantId(String tenantId);
}

