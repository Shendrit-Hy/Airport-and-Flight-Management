package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.SupportDTO;
import com.mbi_re.airport_management.model.SupportRequest;
import com.mbi_re.airport_management.repository.SupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SupportService {

    private final SupportRepository supportRepository;

    @Autowired
    public SupportService(SupportRepository supportRepository) {
        this.supportRepository = supportRepository;
    }

    public SupportRequest fileComplaint(SupportDTO dto) {
        SupportRequest request = SupportRequest.builder()
                .subject(dto.getSubject())
                .message(dto.getMessage())
                .email(dto.getEmail())
                .createdAt(LocalDateTime.now())
                .build();
        return supportRepository.save(request);
    }

    public List<SupportRequest> getAllComplaints() {
        return supportRepository.findAll();
    }
}
