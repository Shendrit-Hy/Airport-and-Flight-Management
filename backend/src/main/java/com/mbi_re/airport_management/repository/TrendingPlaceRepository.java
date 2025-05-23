package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.TrendingPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrendingPlaceRepository extends JpaRepository<TrendingPlace, Long> {
    List<TrendingPlace> findBySeasonAndTenantId(String season, String tenantId);
    List<TrendingPlace> findByTenantId(String tenantId);
}
