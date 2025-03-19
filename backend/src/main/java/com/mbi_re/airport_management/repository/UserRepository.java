package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndTenantId(String username, String tenantId);
    List<User> findAllByTenantId(String tenantId);
}