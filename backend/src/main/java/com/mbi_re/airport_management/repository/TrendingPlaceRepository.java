package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.TrendingPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * {@code TrendingPlaceRepository} ofron metoda për qasje dhe menaxhim të entiteteve {@link TrendingPlace},
 * me mbështetje për filtrime sipas sezonit dhe tenant-it në një sistem multi-tenant.
 */
public interface TrendingPlaceRepository extends JpaRepository<TrendingPlace, Long> {

    /**
     * Gjen vendet trend për një sezon të caktuar dhe tenant.
     *
     * @param season   sezoni i kërkuar (p.sh. "Summer", "Winter")
     * @param tenantId identifikuesi i tenant-it
     * @return listë me vende trend për sezonin dhe tenant-in e specifikuar
     */
    List<TrendingPlace> findBySeasonAndTenantId(String season, String tenantId);

    /**
     * Gjen të gjitha vendet trend që i përkasin një tenant-i të caktuar.
     *
     * @param tenantId identifikuesi i tenant-it
     * @return listë me entitete {@link TrendingPlace}
     */
    List<TrendingPlace> findByTenantId(String tenantId);
}
