package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.UserDTO;
import com.mbi_re.airport_management.model.User;
import com.mbi_re.airport_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(UserDTO userDTO, String role, String tenantId) {
        User user = new User();
        user.setFullName(userDTO.getFullname());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setCountry(userDTO.getCountry());
        user.setTenantId(tenantId);
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username, String tenantId) {
        return userRepository.findByUsernameAndTenantId(username, tenantId);
    }

    public List<User> getUsersByTenant(String tenantId) {
        return userRepository.findAllByTenantId(tenantId);
    }

    public Optional<User> getUserById(Long id, String tenantId) {
        return userRepository.findById(id).filter(user -> user.getTenantId().equals(tenantId));
    }

    public UserDTO getCurrentUserProfile(String tenantId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByUsernameAndTenantId(username, tenantId);

        return user.map(u -> {
            UserDTO dto = new UserDTO();
            dto.setUsername(u.getUsername());
            dto.setEmail(u.getEmail());
            dto.setFullname(u.getFullName());
            dto.setCountry(u.getCountry());
            dto.setTenantId(u.getTenantId());
            return dto;
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
}