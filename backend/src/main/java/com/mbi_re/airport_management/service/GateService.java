package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.GateDTO;
import com.mbi_re.airport_management.model.Gate;
import com.mbi_re.airport_management.repository.GateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GateService {

    private final GateRepository gateRepository;

    public GateService(GateRepository gateRepository) {
        this.gateRepository = gateRepository;
    }

    public List<Gate> getAllGates(String tenantId) {
        return gateRepository.findByTenantId(tenantId);
    }

    public Gate createGate(GateDTO dto, String tenantId) {
        Gate gate = new Gate();
        gate.setGateNumber(dto.getGateNumber());
        gate.setTenantId(tenantId);
        return gateRepository.save(gate);
    }
}
