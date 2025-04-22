package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.CheckInDTO;
import com.mbi_re.airport_management.model.CheckIn;
import com.mbi_re.airport_management.repository.CheckInRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CheckInService {
    private final CheckInRepository checkInRepository;

    public CheckInService(CheckInRepository checkInRepository) {
        this.checkInRepository = checkInRepository;
    }

    public CheckIn checkInPassenger(CheckInDTO dto) {
        if (checkInRepository.findByBookingId(dto.getBookingId()).isPresent()) {
            throw new RuntimeException("Passenger already checked in");
        }

        CheckIn checkIn = new CheckIn();
        checkIn.setBookingId(dto.getBookingId());
        checkIn.setSeatNumber(dto.getSeatNumber());
        checkIn.setCheckInTime(LocalDateTime.now());
        return checkInRepository.save(checkIn);
    }

    public Optional<CheckIn> getCheckInByBookingId(String bookingId) {
        return checkInRepository.findByBookingId(bookingId);
    }

    public List<CheckIn> getBoardingList() {
        return checkInRepository.findByBoardedFalse();
    }
}

