package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.SeatDTO;
import com.mbi_re.airport_management.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping("/available/{flightId}")
    public List<SeatDTO> getAvailableSeats(
            @RequestHeader("X-Tenant-ID") String tenantId,
            @PathVariable Long flightId
    ) {
        return seatService.getAvailableSeats(flightId, tenantId);
    }

    @GetMapping("/all/{flightId}")
    public List<SeatDTO> getAllSeats(
            @RequestHeader("X-Tenant-ID") String tenantId,
            @PathVariable Long flightId
    ) {
        return seatService.getAllSeats(flightId, tenantId);
    }

    @PutMapping("/{seatId}/unavailable")
    public SeatDTO markSeatAsUnavailable(
            @RequestHeader("X-Tenant-ID") String tenantId,
            @PathVariable Long seatId
    ) {
        return seatService.markSeatAsUnavailable(seatId, tenantId);
    }


}
