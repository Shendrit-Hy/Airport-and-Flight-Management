package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.TrendingPlaceDTO;
import com.mbi_re.airport_management.model.TrendingPlace;
import com.mbi_re.airport_management.service.TrendingPlaceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trending-places")
public class TrendingPlaceController {

    private final TrendingPlaceService service;

    public TrendingPlaceController(TrendingPlaceService service) {
        this.service = service;
    }

    @GetMapping
    public List<TrendingPlace> getTrendingPlaces(@RequestHeader("X-Tenant-ID") String tenantId) {
        TenantContext.setTenantId(tenantId);
        return service.getTrendingPlaces(tenantId);
    }

    @PostMapping
    public TrendingPlace createTrendingPlace(@RequestBody TrendingPlaceDTO dto,
                                             @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantContext.setTenantId(tenantId);
        return service.createTrendingPlace(dto, tenantId);
    }
}
