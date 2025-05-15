package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.SupportDTO;
import com.mbi_re.airport_management.model.SupportRequest;
import com.mbi_re.airport_management.model.User;
import com.mbi_re.airport_management.repository.SupportRepository;
import com.mbi_re.airport_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.mbi_re.airport_management.config.TenantContext;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class SupportService {

    private final SupportRepository supportRepository;
    private final UserRepository userRepository;
    @Autowired
    public SupportService(SupportRepository supportRepository,UserRepository userRepository) {
        this.supportRepository = supportRepository;
        this.userRepository = userRepository;
    }

    public SupportRequest fileComplaint(SupportDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String tenantId = dto.getTenantId();
        // Mundësisht përdor tenantId nëse po punon me multi-tenancy
        User user = userRepository.findByUsernameAndTenantId(username, dto.getTenantId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        SupportRequest request = new SupportRequest();
        request.setSubject(dto.getSubject());
        request.setMessage(dto.getMessage());
        request.setEmail(dto.getEmail());
        request.setCreatedAt(LocalDateTime.now());
        request.setUser(user);
        return supportRepository.save(request);
    }

    public List<SupportRequest> getAllComplaints() {
        return supportRepository.findAll();
    }
}
