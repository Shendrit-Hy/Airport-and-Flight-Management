package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.model.Flight;
import com.mbi_re.airport_management.model.FlightStatus;
import com.mbi_re.airport_management.repository.FlightRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service responsible for automatically updating the status of flights based on their scheduled times.
 */
@Service
@RequiredArgsConstructor
public class FlightStatusService {

    @Autowired
    private FlightRepository flightRepository;

    /**
     * Updates the status of all flights every minute based on current time.
     * <p>
     * Status transitions:
     * <ul>
     *     <li>SCHEDULED: More than 60 minutes before departure</li>
     *     <li>BOARDING: Within 60 minutes before departure</li>
     *     <li>IN_AIR: Between departure and arrival</li>
     *     <li>LANDED: After arrival</li>
     *     <li>UNKNOWN: Fallback state</li>
     * </ul>
     */
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateFlightStatuses() {
        List<Flight> flights = flightRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (Flight flight : flights) {
            LocalDateTime departure = LocalDateTime.of(flight.getFlightDate(), flight.getDepartureTime());
            LocalDateTime arrival = LocalDateTime.of(flight.getFlightDate(), flight.getArrivalTime());

            // Adjust for overnight flights
            if (arrival.isBefore(departure)) {
                arrival = arrival.plusDays(1);
            }

            FlightStatus newStatus = determineStatus(now, departure, arrival);

            if (flight.getFlightStatus() != newStatus) {
                flight.setFlightStatus(newStatus);
                flightRepository.save(flight);
            }
        }
    }

    /**
     * Determines the current flight status based on the time now, departure, and arrival.
     *
     * @param now       current time
     * @param departure scheduled departure datetime
     * @param arrival   scheduled arrival datetime
     * @return the evaluated {@link FlightStatus}
     */
    private FlightStatus determineStatus(LocalDateTime now, LocalDateTime departure, LocalDateTime arrival) {
        if (now.isBefore(departure.minusMinutes(60))) {
            return FlightStatus.SCHEDULED;
        } else if (now.isAfter(departure.minusMinutes(60)) && now.isBefore(departure)) {
            return FlightStatus.BOARDING;
        } else if (now.isAfter(departure) && now.isBefore(arrival)) {
            return FlightStatus.IN_AIR;
        } else if (now.isAfter(arrival)) {
            return FlightStatus.LANDED;
        } else {
            return FlightStatus.UNKNOWN;
        }
    }
}
