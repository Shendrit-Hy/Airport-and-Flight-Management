package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.UserDTO;
import com.mbi_re.airport_management.model.Country;
import com.mbi_re.airport_management.model.Role;
import com.mbi_re.airport_management.model.User;
import com.mbi_re.airport_management.repository.CountryRepository;
import com.mbi_re.airport_management.repository.UserRepository;
import com.mbi_re.airport_management.config.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service handling user registration, retrieval, and profile management logic.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registers a new user under the specified tenant.
     * If it is the first user for the tenant, assigns the ADMIN role, otherwise USER role.
     *
     * @param userDTO  the user registration data transfer object
     * @param tenantId the tenant ID to associate the new user with
     * @return the newly created User entity
     * @throws RuntimeException if the specified country is not found within the tenant
     */
    public User registerUser(final UserDTO userDTO, final String tenantId) {
        List<User> existingUsers = userRepository.findAllByTenantId(tenantId);
        Role assignedRole = existingUsers.isEmpty() ? Role.ADMIN : Role.USER;

        Country country = countryRepository.findByNameAndTenantId(userDTO.getCountry(), tenantId)
                .orElseThrow(() -> new RuntimeException("Country not found: " + userDTO.getCountry()));

        User user = new User();
        user.setFullName(userDTO.getFullname());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setCountry(country);
        user.setTenantId(tenantId);
        user.setRole(assignedRole);

        return userRepository.save(user);
    }

    /**
     * Finds a user by username and tenant ID.
     *
     * @param username the username of the user
     * @param tenantId the tenant ID to scope the search
     * @return an Optional containing the User if found, otherwise empty
     */
    public Optional<User> findByUsername(final String username, final String tenantId) {
        return userRepository.findByUsernameAndTenantId(username, tenantId);
    }

    /**
     * Retrieves all users associated with a given tenant.
     *
     * @param tenantId the tenant ID to filter users
     * @return a list of User entities belonging to the tenant
     */
    public List<User> getUsersByTenant(final String tenantId) {
        return userRepository.findAllByTenantId(tenantId);
    }

    /**
     * Updates the profile of the currently authenticated user.
     * Password will only be updated if provided and non-empty.
     *
     * @param updatedUserDTO the DTO containing updated profile information
     * @return the updated UserDTO representation
     * @throws RuntimeException if the current user or specified country is not found
     */
    public UserDTO updateUserProfile(final UserDTO updatedUserDTO) {
        final String tenantId = TenantContext.getTenantId();
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsernameAndTenantId(username, tenantId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Country country = countryRepository.findByNameAndTenantId(updatedUserDTO.getCountry(), tenantId)
                .orElseThrow(() -> new RuntimeException("Country not found: " + updatedUserDTO.getCountry()));

        user.setFullName(updatedUserDTO.getFullname());
        user.setEmail(updatedUserDTO.getEmail());
        user.setCountry(country);

        if (updatedUserDTO.getPassword() != null && !updatedUserDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUserDTO.getPassword()));
        }

        userRepository.save(user);

        return mapToDTO(user);
    }

    /**
     * Retrieves the profile of the currently authenticated user.
     *
     * @return the current user's profile as a UserDTO
     * @throws RuntimeException if the user is not found
     */
    public UserDTO getCurrentUserProfile() {
        final String tenantId = TenantContext.getTenantId();
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsernameAndTenantId(username, tenantId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToDTO(user);
    }

    /**
     * Retrieves a user by their ID, scoped by the current tenant.
     * Uses caching keyed by user ID and tenant ID for efficiency.
     *
     * @param id the ID of the user to retrieve
     * @return an Optional containing the User if found and tenant matches, otherwise empty
     */
    @Cacheable(value = "usersById", key = "#id + '_' + T(com.mbi_re.airport_management.config.TenantContext).getTenantId()")
    public Optional<User> getUserById(final Long id) {
        final String tenantId = TenantContext.getTenantId();
        return userRepository.findById(id)
                .filter(user -> tenantId.equals(user.getTenantId()));
    }

    /**
     * Helper method to map a User entity to a UserDTO.
     *
     * @param user the User entity
     * @return a UserDTO populated with user details
     */
    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFullname(user.getFullName());
        dto.setCountry(user.getCountry().getName());
        dto.setTenantId(user.getTenantId());
        return dto;
    }
}
