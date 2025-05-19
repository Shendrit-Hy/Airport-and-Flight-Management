// REPOSITORY
package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.ParkingPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingPriceRepository extends JpaRepository<ParkingPrice, Long> {
}