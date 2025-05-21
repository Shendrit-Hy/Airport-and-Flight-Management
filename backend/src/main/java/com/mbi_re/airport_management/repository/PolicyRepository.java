package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

    /**
     * Finds all policies for a given tenant ID.
     *
     * @param tenantId the tenant ID to filter by
     * @return list of policies for the given tenant
     */
    List<Policy> findByTenantId(String tenantId);
}
