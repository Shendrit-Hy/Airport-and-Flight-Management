package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.UserDTO;
import com.mbi_re.airport_management.model.Role;
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

    public User registerUser(UserDTO userDTO, String tenantId) {
        List<User> existingUsers = userRepository.findAllByTenantId(tenantId);

        Role assignedRole = existingUsers.isEmpty() ? Role.ADMIN : Role.USER;

        User user = new User();
        user.setFullName(userDTO.getFullname());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setCountry(userDTO.getCountry());
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

    public UserDTO updateUserProfile(UserDTO updatedUserDTO, String tenantId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsernameAndTenantId(username, tenantId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        user.setFullName(updatedUserDTO.getFullname());
        user.setEmail(updatedUserDTO.getEmail());
        user.setCountry(updatedUserDTO.getCountry());


        if (updatedUserDTO.getPassword() != null && !updatedUserDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUserDTO.getPassword()));
        }


        userRepository.save(user);

        UserDTO updatedDTO = new UserDTO();
        updatedDTO.setUsername(user.getUsername());
        updatedDTO.setEmail(user.getEmail());
        updatedDTO.setFullname(user.getFullName());
        updatedDTO.setCountry(user.getCountry());
        updatedDTO.setTenantId(user.getTenantId());

        return updatedDTO;
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
    public Optional<User> getUserById(Long id, String tenantId) {
        return userRepository.findById(id).filter(user -> user.getTenantId().equals(tenantId));
    }
}