package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.StaffDTO;
import com.mbi_re.airport_management.model.Staff;
import com.mbi_re.airport_management.repository.StaffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StaffServiceTest {

    @Mock
    private StaffRepository staffRepository;

    @InjectMocks
    private StaffService staffService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        TenantContext.setTenantId("tenant123"); // simulate tenant context
    }

    @Test
    void testCreate() {
        StaffDTO dto = getSampleDTO();
        Staff saved = getSampleEntity();

        when(staffRepository.save(any(Staff.class))).thenReturn(saved);

        StaffDTO result = staffService.create(dto);

        assertNotNull(result);
        assertEquals(dto.getName(), result.getName());
        verify(staffRepository, times(1)).save(any(Staff.class));
    }

    @Test
    void testGetAll() {
        List<Staff> list = List.of(getSampleEntity(), getSampleEntity());
        when(staffRepository.findByTenantId("tenant123")).thenReturn(list);

        List<StaffDTO> result = staffService.getAll("tenant123");

        assertEquals(2, result.size());
        verify(staffRepository, times(1)).findByTenantId("tenant123");
    }

    @Test
    void testGetByIdAndTenantId_Success() {
        Staff staff = getSampleEntity();
        when(staffRepository.findByIdAndTenantId(1L, "tenant123")).thenReturn(Optional.of(staff));

        StaffDTO result = staffService.getByIdAndTenantId(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testGetByIdAndTenantId_NotFound() {
        when(staffRepository.findByIdAndTenantId(99L, "tenant123")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                staffService.getByIdAndTenantId(99L));

        assertEquals("Staff not found", ex.getMessage());
    }

    @Test
    void testUpdate_Success() {
        Staff existing = getSampleEntity();
        StaffDTO updatedDTO = getSampleDTO();
        updatedDTO.setName("Updated Name");

        when(staffRepository.findByIdAndTenantId(1L, "tenant123")).thenReturn(Optional.of(existing));
        when(staffRepository.save(any(Staff.class))).thenReturn(existing);

        StaffDTO result = staffService.update(1L, updatedDTO);

        assertEquals("Updated Name", result.getName());
        verify(staffRepository).save(any(Staff.class));
    }

    @Test
    void testDelete_Success() {
        Staff existing = getSampleEntity();
        when(staffRepository.findByIdAndTenantId(1L, "tenant123")).thenReturn(Optional.of(existing));

        staffService.delete(1L);

        verify(staffRepository).delete(existing);
    }

    @Test
    void testDelete_NotFound() {
        when(staffRepository.findByIdAndTenantId(1L, "tenant123")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                staffService.delete(1L));

        assertEquals("Staff not found", ex.getMessage());
    }

    private StaffDTO getSampleDTO() {
        StaffDTO dto = new StaffDTO();
        dto.setId(1L);
        dto.setName("John Doe");
        dto.setRole("Security");
        dto.setEmail("john@example.com");
        dto.setShiftStart(LocalTime.of(9, 0));
        dto.setShiftEnd(LocalTime.of(17, 0));
        dto.setTenantId("tenant123");
        return dto;
    }

    private Staff getSampleEntity() {
        Staff staff = new Staff();
        staff.setId(1L);
        staff.setName("John Doe");
        staff.setRole("Security");
        staff.setEmail("john@example.com");
        staff.setShiftStart(LocalTime.of(9, 0));
        staff.setShiftEnd(LocalTime.of(17, 0));
        staff.setTenantId("tenant123");
        return staff;
    }
}
