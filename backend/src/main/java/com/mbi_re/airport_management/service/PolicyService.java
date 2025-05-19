package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.PolicyDTO;
import com.mbi_re.airport_management.model.Policy;
import com.mbi_re.airport_management.repository.PolicyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyService {

    private final PolicyRepository policyRepository;

    public PolicyService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    public Policy createPolicy(PolicyDTO dto) {
        Policy policy = new Policy();
        policy.setTitle(dto.getTitle());
        policy.setContent(dto.getContent());
        policy.setType(dto.getType());
        return policyRepository.save(policy);
    }
}