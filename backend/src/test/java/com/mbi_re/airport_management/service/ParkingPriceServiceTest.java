package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.ParkingPriceDTO;
import com.mbi_re.airport_management.model.ParkingPrice;
import com.mbi_re.airport_management.repository.ParkingPriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingPriceServiceTest {

    private ParkingPriceRepository repository;
    private ParkingPriceService service;

    @BeforeEach
    void setUp() {
        repository = mock(ParkingPriceRepository.class);
        service = new ParkingPriceService(repository);
    }

    @Test
    void calculateAndSave_under15Minutes_shouldBeFree() {
        ParkingPriceDTO dto = new ParkingPriceDTO(10, 0, 10, 10, "tenant1");
        ParkingPrice expected = new ParkingPrice(null, 10, 0, 10, 10, 0.0, "tenant1");

        when(repository.save(any(ParkingPrice.class))).thenReturn(expected);

        ParkingPrice result = service.calculateAndSave(dto);

        assertEquals(0.0, result.getPrice());
        verify(repository).save(any(ParkingPrice.class));
    }

    @Test
    void calculateAndSave_twoHours_shouldBe2Euro() {
        ParkingPriceDTO dto = new ParkingPriceDTO(8, 0, 10, 0, "tenant1");

        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ParkingPrice result = service.calculateAndSave(dto);

        assertEquals(2.0, result.getPrice());
    }

    @Test
    void calculateAndSave_sixHours_shouldBe4Euro() {
        ParkingPriceDTO dto = new ParkingPriceDTO(8, 0, 14, 0, "tenant1");

        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ParkingPrice result = service.calculateAndSave(dto);

        assertEquals(4.0, result.getPrice());
    }




    @Test
    void getAllByTenant_returnsResults() {
        List<ParkingPrice> mockList = List.of(new ParkingPrice());
        when(repository.findAllByTenantId("tenant1")).thenReturn(mockList);

        List<ParkingPrice> result = service.getAllByTenant("tenant1");

        assertEquals(1, result.size());
        verify(repository).findAllByTenantId("tenant1");
    }
}
