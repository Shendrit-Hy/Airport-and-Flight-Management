package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.PolicyDTO;
import com.mbi_re.airport_management.model.Policy;
import com.mbi_re.airport_management.repository.PolicyRepository;
import com.mbi_re.airport_management.utils.TenantUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * Retrieves all policies associated with the specified tenant.
     *
     * <p>This method can be cached for performance optimization if needed,
     * but currently is not annotated with caching annotations.</p>
     *
     * @param tenantId the tenant identifier whose policies are to be retrieved
     * @return list of {@link Policy} entities for the given tenant
     */
    public List<Policy> getAllPolicies(String tenantId) {
        return policyRepository.findByTenantId(tenantId);
    }

    /**
     * Creates a new policy for the current tenant.
     *
     * <p>The tenant ID is fetched via {@link TenantUtil#getCurrentTenant()} and
     * explicitly assigned to the new policy before saving.</p>
     *
     * <p>This method evicts all entries in the "policies" cache to ensure
     * cache consistency after a new policy is added.</p>
     *
     * @param dto the {@link PolicyDTO} containing details of the policy to be created
     * @return the newly created {@link Policy} entity
     */
    @CacheEvict(value = "policies", allEntries = true)
    public Policy createPolicy(PolicyDTO dto) {
        String tenantId = TenantUtil.getCurrentTenant();
        Policy policy = new Policy();
        policy.setTitle(dto.getTitle());
        policy.setContent(dto.getContent());
        policy.setType(dto.getType());
        policy.setTenantId(tenantId);
        return policyRepository.save(policy);
    }

    /**
     * Deletes the policy identified by the given ID for the specified tenant.
     *
     * <p>This operation is transactional to ensure data integrity.</p>
     *
     * @param id       the ID of the policy to delete
     * @param tenantId the tenant identifier used to scope the deletion
     */
    @Transactional
    public void deletePolicy(Long id, String tenantId) {
        policyRepository.deleteByIdAndTenantId(id, tenantId);
    }
}
