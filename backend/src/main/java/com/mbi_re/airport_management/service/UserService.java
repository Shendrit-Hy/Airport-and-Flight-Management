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

    public UserDTO getCurrentUserProfile(String tenantId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameAndTenantId(username, tenantId)
                .map(user -> {
                    UserDTO dto = new UserDTO();
                    dto.setUsername(user.getUsername());
                    dto.setEmail(user.getEmail());
                    dto.setTenantId(user.getTenantId());
                    return dto;
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    public Optional<User> findByUsername(String username, String tenantId) {
        return userRepository.findByUsernameAndTenantId(username, tenantId);
    }

    public List<User> getUsersByTenant(String tenantId) {
        return userRepository.findAllByTenantId(tenantId);
    }

    public UserDTO updateUserProfile(UserDTO updatedUser, String tenantId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsernameAndTenantId(username, tenantId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Përditëso fushat që lejon për ndryshim
        user.setEmail(updatedUser.getEmail());
        user.setUsername(updatedUser.getUsername());
        // Nota: Nuk e përditësojmë password-in këtu (mund të bëhet në endpoint tjetër)

        userRepository.save(user);

        // Kthejmë DTO-në e përditësuar
        UserDTO dto = new UserDTO();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setTenantId(user.getTenantId());
        return dto;
    }

    public Optional<User> getUserById(Long id, String tenantId) {
        return userRepository.findById(id).filter(user -> user.getTenantId().equals(tenantId));
    }

}