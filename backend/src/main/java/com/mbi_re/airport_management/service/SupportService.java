package com.mbi_re.airport_management.service;
import com.mbi_re.airport_management.model.SupportRequest;
import com.mbi_re.airport_management.repository.SupportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class SupportService {
    private final SupportRepository repository;

    public SupportService(SupportRepository repository) {
        this.repository = repository;
    }

    public SupportRequest fileComplaint(SupportRequest request) {
        request.setCreatedAt(LocalDateTime.now());
        return repository.save(request);
    }

    public List<SupportRequest> getAll() {
        return repository.findAll();
    }
}
