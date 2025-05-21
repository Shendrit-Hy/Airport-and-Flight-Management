package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.PolicyDTO;
import com.mbi_re.airport_management.model.Policy;
import com.mbi_re.airport_management.repository.PolicyRepository;
import com.mbi_re.airport_management.utils.TenantUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing company policy.
 */
@Service
public class PolicyService {

    private final PolicyRepository policyRepository;

    public PolicyService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    /**
     * Retrieves all policies associated with the current tenant.
     * The result is cached to improve read performance.
     *
     * @return list of policies for the current tenant
     */
    @Cacheable(value = "policies", key = "#root.methodName + '_' + T(com.mbi_re.airport_management.utils.TenantUtil).getCurrentTenant()")
    public List<Policy> getAllPolicies() {
        String tenantId = TenantUtil.getCurrentTenant();
        return policyRepository.findByTenantId(tenantId);
    }

    /**
     * Creates a new policy for the tenant provided in the DTO.
     * Cache is evicted upon creation to maintain consistency.
     *
     * @param dto the policy data transfer object containing the policy details and tenant ID
     * @return the created Policy entity
     */
    @CacheEvict(value = "policies", allEntries = true)
    public Policy createPolicy(PolicyDTO dto) {
        String tenantId = TenantUtil.getCurrentTenant();
        Policy policy = new Policy();
        policy.setTitle(dto.getTitle());
        policy.setContent(dto.getContent());
        policy.setType(dto.getType());
        policy.setTenantId(tenantId); // Explicitly setting the tenant ID
        return policyRepository.save(policy);
    }
}
