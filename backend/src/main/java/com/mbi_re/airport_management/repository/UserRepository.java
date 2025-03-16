package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByTenantId(String tenantId);
}

