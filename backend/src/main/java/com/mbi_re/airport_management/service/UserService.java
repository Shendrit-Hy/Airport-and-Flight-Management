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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(UserDTO userDTO, String tenantId) {
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

    public Optional<User> findByUsername(String username, String tenantId) {
        return userRepository.findByUsernameAndTenantId(username, tenantId);
    }

    public List<User> getUsersByTenant(String tenantId) {
        return userRepository.findAllByTenantId(tenantId);
    }

    public UserDTO updateUserProfile(UserDTO updatedUserDTO) {
        final String tenantId = TenantContext.getTenantId();
        final String username = getAuthenticatedUsername();

        User user = userRepository.findByUsernameAndTenantId(username, tenantId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Country country = countryRepository.findByNameAndTenantId(updatedUserDTO.getCountry(), tenantId)
                .orElseThrow(() -> new RuntimeException("Country not found: " + updatedUserDTO.getCountry()));

        user.setFullName(updatedUserDTO.getFullname());
        user.setEmail(updatedUserDTO.getEmail());
        user.setUsername(updatedUserDTO.getUsername());
        user.setCountry(country);

        if (updatedUserDTO.getPassword() != null && !updatedUserDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUserDTO.getPassword()));
        }

        User saved = userRepository.save(user);
        return mapToDTO(saved);
    }

    @Cacheable(value = "userProfiles", key = "'profile_' + T(com.mbi_re.airport_management.config.TenantContext).getTenantId()")
    public UserDTO getCurrentUserProfile() {
        final String tenantId = TenantContext.getTenantId();
        final String username = getAuthenticatedUsername();

        User user = userRepository.findByUsernameAndTenantId(username, tenantId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToDTO(user);
    }

    @Cacheable(value = "usersById", key = "#id + '_' + T(com.mbi_re.airport_management.config.TenantContext).getTenantId()")
    public Optional<User> getUserById(Long id) {
        final String tenantId = TenantContext.getTenantId();
        return userRepository.findById(id)
                .filter(user -> tenantId.equals(user.getTenantId()));
    }

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

    private String getAuthenticatedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        } else {
            return principal.toString();
        }
    }
}
