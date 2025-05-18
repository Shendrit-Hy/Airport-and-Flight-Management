package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByIdAndTenantId(Long id, String tenantId);
    List<Staff> findByTenantId(String tenantId);
}
