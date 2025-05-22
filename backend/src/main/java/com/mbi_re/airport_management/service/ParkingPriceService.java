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
     * Calculates the parking price based on entry and exit time, applies tenant context,
     * and persists the calculated result.
     *
     * @param dto ParkingPriceDTO containing entry/exit times and tenant ID
     * @return saved ParkingPrice entity
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
     * Checks if the parking interval contains night-time hours (18:00 to 06:00).
     *
     * @param entry Entry time
     * @param exit  Exit time
     * @return true if any portion of the time range falls in night hours
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
     * Returns all parking price records for the given tenant.
     * This method is cacheable to reduce database load.
     *
     * @param tenantId Tenant identifier
     * @return list of parking prices
     */
    @Cacheable(value = "parkingPrices", key = "#tenantId")
    public List<ParkingPrice> getAllByTenant(String tenantId) {
        return repository.findAllByTenantId(tenantId);
    }
}
