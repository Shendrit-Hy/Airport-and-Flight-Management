package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.PolicyDTO;
import com.mbi_re.airport_management.model.Policy;
import com.mbi_re.airport_management.repository.PolicyRepository;
import com.mbi_re.airport_management.utils.TenantUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PolicyServiceTest {

    private PolicyRepository policyRepository;
    private PolicyService policyService;

    @BeforeEach
    void setUp() {
        policyRepository = mock(PolicyRepository.class);
        policyService = new PolicyService(policyRepository);
    }

    @Test
    void getAllPolicies_shouldReturnPoliciesForTenant() {
        String tenantId = "tenant123";

        Policy policy = new Policy();
        policy.setId(1L);
        policy.setTitle("Policy Title");
        policy.setContent("Policy Content");
        policy.setType("GENERAL");
        policy.setTenantId(tenantId);

        List<Policy> mockPolicies = List.of(policy);

        try (MockedStatic<TenantUtil> tenantUtilMock = mockStatic(TenantUtil.class)) {
            tenantUtilMock.when(TenantUtil::getCurrentTenant).thenReturn(tenantId);
            when(policyRepository.findByTenantId(tenantId)).thenReturn(mockPolicies);

            List<Policy> result = policyService.getAllPolicies();

            assertEquals(1, result.size());
            assertEquals("Policy Title", result.get(0).getTitle());
            assertEquals(tenantId, result.get(0).getTenantId());
        }
    }

    @Test
    void createPolicy_shouldSaveWithCorrectTenantId() {
        String tenantId = "tenant456";

        PolicyDTO dto = new PolicyDTO();
        dto.setTitle("Security Policy");
        dto.setContent("No access after hours");
        dto.setType("SECURITY");

        Policy savedPolicy = new Policy();
        savedPolicy.setId(1L);
        savedPolicy.setTitle(dto.getTitle());
        savedPolicy.setContent(dto.getContent());
        savedPolicy.setType(dto.getType());
        savedPolicy.setTenantId(tenantId);

        try (MockedStatic<TenantUtil> tenantUtilMock = mockStatic(TenantUtil.class)) {
            tenantUtilMock.when(TenantUtil::getCurrentTenant).thenReturn(tenantId);
            when(policyRepository.save(any())).thenReturn(savedPolicy);

            Policy result = policyService.createPolicy(dto);

            assertEquals(dto.getTitle(), result.getTitle());
            assertEquals(dto.getContent(), result.getContent());
            assertEquals(dto.getType(), result.getType());
            assertEquals(tenantId, result.getTenantId());

            verify(policyRepository).save(any(Policy.class));
        }
    }
}
