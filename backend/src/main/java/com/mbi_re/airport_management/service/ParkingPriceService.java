package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.ParkingPriceDTO;
import com.mbi_re.airport_management.model.ParkingPrice;
import com.mbi_re.airport_management.repository.ParkingPriceRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Service layer responsible for parking price calculations and persistence.
 */
@Service
public class ParkingPriceService {

    private final ParkingPriceRepository repository;

    public ParkingPriceService(ParkingPriceRepository repository) {
        this.repository = repository;
    }

    /**
     * Calculates the parking price based on the provided entry and exit times.
     * Applies tenant context from the DTO, persists the calculated price, and returns the saved entity.
     * <p>
     * Pricing rules:
     * <ul>
     *     <li>Up to 15 minutes free</li>
     *     <li>Up to 2 hours: 2 currency units</li>
     *     <li>Up to 6 hours: 4 currency units</li>
     *     <li>Up to 12 hours: 6 currency units</li>
     *     <li>Up to 24 hours: 8 currency units</li>
     *     <li>More than 24 hours: 10 currency units per day (rounded up)</li>
     *     <li>Additional 1 currency unit if parking includes night hours (18:00 - 06:00)</li>
     * </ul>
     *
     * @param dto Data Transfer Object containing entry hour, entry minute, exit hour, exit minute, and tenant ID
     * @return the persisted ParkingPrice entity with calculated price and times
     */
    public ParkingPrice calculateAndSave(ParkingPriceDTO dto) {
        LocalTime entry = LocalTime.of(dto.getEntryHour(), dto.getEntryMinute());
        LocalTime exit = LocalTime.of(dto.getExitHour(), dto.getExitMinute());

        if (exit.isBefore(entry)) {
            exit = exit.plusHours(24);
        }

        long minutes = ChronoUnit.MINUTES.between(entry, exit);
        long hours = (long) Math.ceil(minutes / 60.0);

        double price;
        if (minutes <= 15) {
            price = 0;
        } else if (hours <= 2) {
            price = 2;
        } else if (hours <= 6) {
            price = 4;
        } else if (hours <= 12) {
            price = 6;
        } else if (hours <= 24) {
            price = 8;
        } else {
            price = 10 * Math.ceil(hours / 24.0);
        }

        if (containsNightHours(entry, exit) && price > 0) {
            price += 1;
        }

        ParkingPrice pp = new ParkingPrice(null,
                dto.getEntryHour(),
                dto.getEntryMinute(),
                dto.getExitHour(),
                dto.getExitMinute(),
                price,
                dto.getTenantId());

        return repository.save(pp);
    }

    /**
     * Checks if the parking period contains any night-time hours.
     * Night-time is defined as the interval from 18:00 (inclusive) to 06:00 (exclusive).
     *
     * @param entry the entry time of parking
     * @param exit  the exit time of parking
     * @return {@code true} if any minute within the parking duration falls in night hours; {@code false} otherwise
     */
    private boolean containsNightHours(LocalTime entry, LocalTime exit) {
        for (int i = 0; i <= ChronoUnit.MINUTES.between(entry, exit); i++) {
            LocalTime current = entry.plusMinutes(i).truncatedTo(ChronoUnit.MINUTES);
            if (current.isAfter(LocalTime.of(17, 59)) || current.isBefore(LocalTime.of(6, 0))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves all parking price records associated with the specified tenant.
     * <p>
     * This method uses caching to optimize performance for repeated tenant queries.
     *
     * @param tenantId the tenant identifier used to filter parking prices
     * @return a list of {@link ParkingPrice} entities belonging to the tenant
     */
    @Cacheable(value = "parkingPrices", key = "#tenantId")
    public List<ParkingPrice> getAllByTenant(String tenantId) {
        return repository.findAllByTenantId(tenantId);
    }
}
