package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity.
 * Supports multi-tenant queries.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by username.
     *
     * @param username The username.
     * @return Optional containing the user if found.
     */
    Optional<User> findByUsername(String username); // 🔹 Shto këtë

    /**
     * Finds a user by username and tenant.
     *
     * @param username The username.
     * @param tenantId The tenant ID.
     * @return Optional containing the user if found.
     */
    Optional<User> findByUsernameAndTenantId(String username, String tenantId);

    /**
     * Retrieves all users belonging to a tenant.
     *
     * @param tenantId The tenant ID.
     * @return List of users.
     */
    List<User> findAllByTenantId(String tenantId);
}
