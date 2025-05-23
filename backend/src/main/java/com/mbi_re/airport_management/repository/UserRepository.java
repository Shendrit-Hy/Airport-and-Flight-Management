package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * {@code UserRepository} ofron metoda për menaxhimin e entitetit {@link User},
 * me mbështetje për filtrime në bazë të {@code tenantId} në arkitekturë multi-tenant.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Gjen një përdorues në bazë të {@code username}.
     *
     * @param username emri i përdoruesit
     * @return {@link Optional} që përmban përdoruesin nëse gjendet
     */
    Optional<User> findByUsername(String username);

    /**
     * Gjen një përdorues në bazë të {@code username} dhe {@code tenantId}.
     *
     * @param username emri i përdoruesit
     * @param tenantId identifikuesi i tenant-it
     * @return {@link Optional} që përmban përdoruesin nëse ekziston për tenant-in
     */
    Optional<User> findByUsernameAndTenantId(String username, String tenantId);

    /**
     * Kthen të gjithë përdoruesit që i përkasin një tenant-i specifik.
     *
     * @param tenantId identifikuesi i tenant-it
     * @return listë me përdorues
     */
    List<User> findAllByTenantId(String tenantId);
}
