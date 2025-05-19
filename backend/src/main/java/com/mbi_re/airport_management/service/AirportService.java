package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.AirportDTO;
import com.mbi_re.airport_management.model.Airport;
import com.mbi_re.airport_management.repository.AirportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportService {

    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public List<Airport> getAllAirports(String tenantId) {
        return airportRepository.findByTenantId(tenantId);
    }

    public Airport createAirport(AirportDTO airportDTO, String tenantId) {
        Airport airport = new Airport();
        airport.setName(airportDTO.getName());
        airport.setCode(airportDTO.getCode());
        airport.setCountry(airportDTO.getCountry());
        airport.setCity(airportDTO.getCity());
        airport.setTimezone(airportDTO.getTimezone());
        airport.setTenantId(tenantId);
        return airportRepository.save(airport);
    }

    public void deleteAirport(Long id) {
        airportRepository.deleteById(id);
    }
}
