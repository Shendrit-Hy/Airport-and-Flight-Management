package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.CityDTO;
import com.mbi_re.airport_management.model.City;
import com.mbi_re.airport_management.model.Country;
import com.mbi_re.airport_management.repository.CityRepository;
import com.mbi_re.airport_management.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CityServiceTest {

    private CityRepository cityRepository;
    private CountryRepository countryRepository;
    private CityService cityService;

    @BeforeEach
    void setUp() {
        cityRepository = mock(CityRepository.class);
        countryRepository = mock(CountryRepository.class);
        cityService = new CityService(cityRepository, countryRepository);
        TenantContext.setTenantId("tenant1");
    }

    @Test
    void getAllCities_returnsCityDTOList() {
        Country country = new Country();
        country.setId(100L);

        City city = new City();
        city.setId(1L);
        city.setName("City A");
        city.setCountry(country);
        city.setTenantId("tenant1");

        when(cityRepository.findAllByTenantId("tenant1")).thenReturn(List.of(city));

        List<CityDTO> result = cityService.getAllCities();

        assertEquals(1, result.size());
        assertEquals("City A", result.get(0).getName());
        assertEquals(100L, result.get(0).getCountryId());
    }

    @Test
    void getCitiesByCountry_returnsCitiesForCountry() {
        Country country = new Country();
        country.setId(200L);

        City city = new City();
        city.setId(2L);
        city.setName("City B");
        city.setCountry(country);
        city.setTenantId("tenant1");

        when(cityRepository.findByCountryIdAndTenantId(200L, "tenant1"))
                .thenReturn(List.of(city));

        List<CityDTO> result = cityService.getCitiesByCountry(200L);

        assertEquals(1, result.size());
        assertEquals("City B", result.get(0).getName());
        assertEquals(200L, result.get(0).getCountryId());
    }

    @Test
    void createCity_createsAndReturnsCityDTO() {
        CityDTO dto = new CityDTO(null, "New City", 300L);

        Country country = new Country();
        country.setId(300L);

        City savedCity = new City();
        savedCity.setId(3L);
        savedCity.setName("New City");
        savedCity.setCountry(country);
        savedCity.setTenantId("tenant1");

        when(countryRepository.findById(300L)).thenReturn(Optional.of(country));
        when(cityRepository.save(any(City.class))).thenReturn(savedCity);

        CityDTO result = cityService.createCity(dto);

        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("New City", result.getName());
        assertEquals(300L, result.getCountryId());

        ArgumentCaptor<City> captor = ArgumentCaptor.forClass(City.class);
        verify(cityRepository).save(captor.capture());
        assertEquals("tenant1", captor.getValue().getTenantId());
    }

    @Test
    void createCity_throwsExceptionIfCountryNotFound() {
        CityDTO dto = new CityDTO(null, "No Country City", 999L);
        when(countryRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> cityService.createCity(dto));
        assertEquals("Country not found", ex.getMessage());
    }

    @Test
    void deleteCity_deletesByIdAndTenantId() {
        cityService.deleteCity(10L);
        verify(cityRepository, times(1)).deleteByIdAndTenantId(10L, "tenant1");
    }
}
