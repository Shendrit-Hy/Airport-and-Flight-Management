package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.AirportDTO;
import com.mbi_re.airport_management.model.Airport;
import com.mbi_re.airport_management.model.City;
import com.mbi_re.airport_management.model.Country;
import com.mbi_re.airport_management.repository.AirportRepository;
import com.mbi_re.airport_management.repository.CityRepository;
import com.mbi_re.airport_management.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AirportServiceTest {

    private AirportRepository airportRepository;
    private CountryRepository countryRepository;
    private CityRepository cityRepository;
    private AirportService airportService;

    @BeforeEach
    void setUp() {
        airportRepository = mock(AirportRepository.class);
        countryRepository = mock(CountryRepository.class);
        cityRepository = mock(CityRepository.class);
        airportService = new AirportService(airportRepository, countryRepository, cityRepository);
    }

    @Test
    void testGetAllAirports() {
        String tenantId = "tenant1";
        List<Airport> airports = Arrays.asList(new Airport(), new Airport());
        when(airportRepository.findByTenantId(tenantId)).thenReturn(airports);

        List<Airport> result = airportService.getAllAirports(tenantId);

        assertEquals(2, result.size());
        verify(airportRepository, times(1)).findByTenantId(tenantId);
    }

    @Test
    void testCreateAirport_Success() {
        String tenantId = "tenant1";

        Country country = new Country();
        country.setId(1L);
        City city = new City();
        city.setId(2L);

        AirportDTO dto = new AirportDTO();
        dto.setName("Test Airport");
        dto.setCode("TST");
        dto.setCountryId(1L);
        dto.setCityId(2L);
        dto.setTimezone("UTC");

        when(countryRepository.findById(1L)).thenReturn(Optional.of(country));
        when(cityRepository.findById(2L)).thenReturn(Optional.of(city));

        Airport savedAirport = new Airport();
        savedAirport.setId(100L);
        when(airportRepository.save(any(Airport.class))).thenReturn(savedAirport);

        Airport result = airportService.createAirport(dto, tenantId);

        assertEquals(100L, result.getId());
        verify(airportRepository).save(any(Airport.class));
    }

    @Test
    void testCreateAirport_CountryNotFound() {
        AirportDTO dto = new AirportDTO();
        dto.setCountryId(1L);
        dto.setCityId(2L);

        when(countryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                airportService.createAirport(dto, "tenant1"));
    }

    @Test
    void testCreateAirport_CityNotFound() {
        AirportDTO dto = new AirportDTO();
        dto.setCountryId(1L);
        dto.setCityId(2L);

        when(countryRepository.findById(1L)).thenReturn(Optional.of(new Country()));
        when(cityRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                airportService.createAirport(dto, "tenant1"));
    }

    @Test
    void testDeleteAirport_Success() {
        Long airportId = 42L;
        String tenantId = "tenant1";

        when(airportRepository.findByIdAndTenantId(airportId, tenantId))
                .thenReturn(Optional.of(new Airport()));

        airportService.deleteAirport(airportId, tenantId);

        verify(airportRepository).deleteByIdAndTenantId(airportId, tenantId);
    }

    @Test
    void testDeleteAirport_NotFound() {
        Long airportId = 42L;
        String tenantId = "tenant1";

        when(airportRepository.findByIdAndTenantId(airportId, tenantId))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                airportService.deleteAirport(airportId, tenantId));
    }
}
