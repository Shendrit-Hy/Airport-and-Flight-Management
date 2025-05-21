// ✅ KLASA E PËRDITËSUAR: TrendingPlaceService.java

package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.TrendingPlaceDTO;
import com.mbi_re.airport_management.model.TrendingPlace;
import com.mbi_re.airport_management.repository.TrendingPlaceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TrendingPlaceService {

    private final TrendingPlaceRepository repository;

    public TrendingPlaceService(TrendingPlaceRepository repository) {
        this.repository = repository;
    }

    public List<TrendingPlace> getTrendingPlaces(String tenantId) {
        String season = determineSeason(LocalDate.now().getMonthValue());
        return repository.findBySeasonAndTenantId(season, tenantId);
    }

    public TrendingPlace createTrendingPlace(TrendingPlaceDTO dto, String tenantId) {
        TrendingPlace place = new TrendingPlace();
        place.setName(dto.getName());
        place.setDescription(dto.getDescription());
        place.setSeason(dto.getSeason());
        place.setImageUrl(dto.getImageUrl());
        place.setTenantId(tenantId);
        return repository.save(place);
    }

    // ✅ Kjo metodë tash kthen SEASON me shkronja të mëdha, që përputhen me të dhënat nga frontend
    private String determineSeason(int month) {
        return switch (month) {
            case 3, 4, 5 -> "SPRING";
            case 6, 7, 8 -> "SUMMER";
            case 9, 10, 11 -> "FALL";
            default -> "WINTER";
        };
    }
}
