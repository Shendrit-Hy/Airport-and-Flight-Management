package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.CountryDTO;
import com.mbi_re.airport_management.model.Country;
import com.mbi_re.airport_management.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CountryServiceTest {

    private CountryRepository countryRepository;
    private CountryService countryService;

    @BeforeEach
    void setUp() {
        countryRepository = mock(CountryRepository.class);
        countryService = new CountryService(countryRepository);

        // Set a mock tenant context value
        TenantContext.setTenantId("tenant123");
    }

    @Test
    void testGetAllCountries_returnsList() {
        Country c1 = new Country();
        c1.setId(1L);
        c1.setName("Germany");
        c1.setTenantId("tenant123");

        Country c2 = new Country();
        c2.setId(2L);
        c2.setName("France");
        c2.setTenantId("tenant123");

        when(countryRepository.findAllByTenantId("tenant123"))
                .thenReturn(List.of(c1, c2));

        List<CountryDTO> result = countryService.getAllCountries();

        assertEquals(2, result.size());
        assertEquals("Germany", result.get(0).getName());
        verify(countryRepository).findAllByTenantId("tenant123");
    }

    @Test
    void testCreateCountry_successfulCreation() {
        CountryDTO dto = new CountryDTO(null, "Italy");

        when(countryRepository.existsByNameAndTenantId("Italy", "tenant123")).thenReturn(false);

        Country saved = new Country();
        saved.setId(10L);
        saved.setName("Italy");
        saved.setTenantId("tenant123");

        when(countryRepository.save(any(Country.class))).thenReturn(saved);

        CountryDTO result = countryService.createCountry(dto);

        assertNotNull(result.getId());
        assertEquals("Italy", result.getName());
        verify(countryRepository).save(any(Country.class));
    }

    @Test
    void testCreateCountry_throwsExceptionIfExists() {
        CountryDTO dto = new CountryDTO(null, "Spain");

        when(countryRepository.existsByNameAndTenantId("Spain", "tenant123")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            countryService.createCountry(dto);
        });

        assertEquals("Country already exists!", ex.getMessage());
        verify(countryRepository, never()).save(any());
    }

    @Test
    void testDeleteCountry_callsRepository() {
        countryService.deleteCountry("ES");

        verify(countryRepository).deleteByCodeAndTenantId("ES", "tenant123");
    }
}
