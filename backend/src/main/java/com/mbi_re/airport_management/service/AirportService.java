package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.model.Airport;
import com.mbi_re.airport_management.repository.AirportRepository;
import com.mbi_re.airport_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AirportService {

    @Autowired
    private AirportRepository airportRepository;

    public Optional<Airport> getAirportById(Long id, String tenantId) {
        return airportRepository.findById(id)
                .filter(a -> a.getTenantId().equals(tenantId));
    }

    public boolean deleteAirportById(Long id, String tenantId) {
        Optional<Airport> airportOpt = airportRepository.findById(id);

        if (airportOpt.isPresent() && airportOpt.get().getTenantId().equals(tenantId)) {
            airportRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Airport> updateAirportById(Long id, String tenantId, Airport updatedAirport) {
        return airportRepository.findByIdAndTenantId(id, tenantId).map(existingAirport -> {
            existingAirport.setName(updatedAirport.getName());
            existingAirport.setLocation(updatedAirport.getLocation());
            existingAirport.setCity(updatedAirport.getCity());
            existingAirport.setCountry(updatedAirport.getCountry());
            existingAirport.setIataCode(updatedAirport.getIataCode());
            existingAirport.setIcaoCode(updatedAirport.getIcaoCode());

            return airportRepository.save(existingAirport);
        });
    }


}
