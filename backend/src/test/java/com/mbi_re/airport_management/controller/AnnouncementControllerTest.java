package com.mbi_re.airport_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbi_re.airport_management.dto.AnnouncementDTO;
import com.mbi_re.airport_management.service.AnnouncementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class AnnouncementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnnouncementService announcementService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAnnouncements_shouldReturnAnnouncements() throws Exception {
        AnnouncementDTO dto = new AnnouncementDTO();
        dto.setTitle("System Maintenance");
        dto.setMessage("Airport systems will be offline.");
        dto.setTenantId("default");

        when(announcementService.getAnnouncementsByTenant("default"))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/announcements")
                        .header("X-Tenant-ID", "default"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("System Maintenance"))
                .andExpect(jsonPath("$[0].message").value("Airport systems will be offline."));
    }

    @Test
    @WithMockUser(roles = "ADMIN") // Simulate admin user
    void createAnnouncement_shouldReturnCreatedAnnouncement() throws Exception {
        AnnouncementDTO requestDto = new AnnouncementDTO();
        requestDto.setTitle("System Maintenance");
        requestDto.setMessage("Airport systems will be offline.");
        requestDto.setTenantId("default");

        AnnouncementDTO responseDto = new AnnouncementDTO();
        responseDto.setTitle("System Maintenance");
        responseDto.setMessage("Airport systems will be offline.");
        responseDto.setTenantId("default");

        when(announcementService.saveAnnouncement(any(AnnouncementDTO.class)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/api/announcements")
                        .header("X-Tenant-ID", "default")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("System Maintenance"))
                .andExpect(jsonPath("$.message").value("Airport systems will be offline."));
    }


}
