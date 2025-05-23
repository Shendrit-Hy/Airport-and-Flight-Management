package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.TrendingPlaceDTO;
import com.mbi_re.airport_management.model.TrendingPlace;
import com.mbi_re.airport_management.repository.TrendingPlaceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class for handling business logic related to trending places.
 * Supports creation, retrieval, and deletion of trending places.
 */
@Service
public class TrendingPlaceService {

    private final TrendingPlaceRepository repository;

    /**
     * Constructs a new TrendingPlaceService with the given repository.
     *
     * @param repository The repository for accessing trending place data.
     */
    public TrendingPlaceService(TrendingPlaceRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves a list of trending places for the current season and tenant.
     * The current season is determined based on the current month.
     *
     * @param tenantId The tenant ID to scope the results.
     * @return A list of trending places for the tenant and current season.
     */
    public List<TrendingPlace> getTrendingPlacesBySeason(String tenantId) {
        String season = determineSeason(LocalDate.now().getMonthValue());

        return repository.findBySeasonAndTenantId(season, tenantId);
    }

    /**
     * Retrieves all trending places for the specified tenant.
     *
     * @param tenantId The tenant ID to scope the results.
     * @return A list of trending places belonging to the tenant.
     */
    public List<TrendingPlace> getTrendingPlaces(String tenantId) {
        return repository.findByTenantId(tenantId);
    }

    /**
     * Creates and saves a new trending place associated with the specified tenant.
     *
     * @param dto      The data transfer object containing trending place details.
     * @param tenantId The tenant ID to associate the new place with.
     * @return The saved TrendingPlace entity.
     */
    public TrendingPlace createTrendingPlace(TrendingPlaceDTO dto, String tenantId) {
        TrendingPlace place = new TrendingPlace();
        place.setName(dto.getName());
        place.setDescription(dto.getDescription());
        place.setSeason(dto.getSeason());
        place.setImageUrl(dto.getImageUrl());
        place.setTenantId(tenantId);
        return repository.save(place);
    }

    /**
     * Deletes a trending place by its ID, verifying tenant ownership.
     *
     * @param id       The ID of the trending place to delete.
     * @param tenantId The tenant ID to validate ownership.
     * @throws IllegalArgumentException if the trending place does not exist or
     *                                  if it does not belong to the specified tenant.
     */
    public void deleteTrendingPlace(Long id, String tenantId) {
        TrendingPlace place = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trending place not found with ID: " + id));

        if (!place.getTenantId().equals(tenantId)) {
            throw new IllegalArgumentException("Unauthorized to delete this trending place.");
        }

        repository.delete(place);
    }

    /**
     * Determines the current season based on the given month.
     *
     * @param month The month value (1 to 12).
     * @return The name of the season in uppercase (e.g., "SPRING", "SUMMER", "FALL", "WINTER").
     */
    private String determineSeason(int month) {
        return switch (month) {
            case 3, 4, 5 -> "SPRING";
            case 6, 7, 8 -> "SUMMER";
            case 9, 10, 11 -> "FALL";
            default -> "WINTER";
        };
    }
}
