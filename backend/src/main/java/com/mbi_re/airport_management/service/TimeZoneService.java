package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.TimeZoneDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsible for managing time zone data.
 * It generates a list of available time zones with their UTC offsets.
 */
@Service
@Tag(name = "TimeZoneService", description = "Service for retrieving time zone information")
public class TimeZoneService {

    /**
     * Retrieves all available time zones with their UTC offsets.
     * This data is static and rarely changes, so it is cacheable.
     *
     * @return a list of {@link TimeZoneDTO} containing time zone ID and offset
     */
    @Cacheable(value = "timezones", key = "#root.methodName") // Cache once per service method (not per tenant)
    public List<TimeZoneDTO> getAllTimeZones() {
        return ZoneId.getAvailableZoneIds().stream()
                .sorted()
                .map(id -> {
                    ZoneId zone = ZoneId.of(id);
                    String offset = ZonedDateTime.now(zone).getOffset().getId(); // e.g. +05:30
                    return new TimeZoneDTO(id, "UTC" + offset);
                })
                .collect(Collectors.toList());
    }
}
