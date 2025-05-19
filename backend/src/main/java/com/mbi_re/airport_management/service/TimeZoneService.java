package com.mbi_re.airport_management.service;


import com.mbi_re.airport_management.dto.TimeZoneDTO;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeZoneService {

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

