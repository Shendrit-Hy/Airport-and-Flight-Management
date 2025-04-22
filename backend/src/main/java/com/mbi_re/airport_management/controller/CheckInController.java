package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.CheckInDTO;
import com.mbi_re.airport_management.model.CheckIn;
import com.mbi_re.airport_management.service.CheckInService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkin")
public class CheckInController {
    private final CheckInService checkInService;

    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    @PostMapping
    public ResponseEntity<CheckIn> checkIn(@RequestBody CheckInDTO dto) {
        CheckIn checkIn = checkInService.checkInPassenger(dto);
        return ResponseEntity.ok(checkIn);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<CheckIn> getCheckIn(@PathVariable String bookingId) {
        return checkInService.getCheckInByBookingId(bookingId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/boarding")
    public ResponseEntity<List<CheckIn>> getBoardingList() {
        return ResponseEntity.ok(checkInService.getBoardingList());
    }
}

