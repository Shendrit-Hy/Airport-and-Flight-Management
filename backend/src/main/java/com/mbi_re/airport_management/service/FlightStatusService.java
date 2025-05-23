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
     * Periodically updates the status of all flights.
     * <p>
     * This method runs every minute and updates each flight's status according to the following rules:
     * <ul>
     *     <li>{@link FlightStatus#SCHEDULED}: More than 60 minutes before departure</li>
     *     <li>{@link FlightStatus#BOARDING}: Within 60 minutes before departure</li>
     *     <li>{@link FlightStatus#IN_AIR}: Between departure and arrival times</li>
     *     <li>{@link FlightStatus#LANDED}: After arrival time</li>
     *     <li>{@link FlightStatus#UNKNOWN}: Fallback status if none of the above conditions apply</li>
     * </ul>
     * <p>
     * Handles overnight flights by adjusting arrival time if it is before departure time.
     */
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateFlightStatuses() {
        List<Flight> flights = flightRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (Flight flight : flights) {
            LocalDateTime departure = LocalDateTime.of(flight.getFlightDate(), flight.getDepartureTime());
            LocalDateTime arrival = LocalDateTime.of(flight.getFlightDate(), flight.getArrivalTime());

            // Adjust for overnight flights (arrival next day)
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
     * Determines the appropriate flight status based on current time relative to departure and arrival.
     *
     * @param now       the current date and time
     * @param departure the scheduled departure date and time
     * @param arrival   the scheduled arrival date and time
     * @return the evaluated {@link FlightStatus} corresponding to the current phase of the flight
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
