package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.UserDTO;
import com.mbi_re.airport_management.model.Role;
import com.mbi_re.airport_management.model.User;
import com.mbi_re.airport_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(Role.valueOf(role));
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
}