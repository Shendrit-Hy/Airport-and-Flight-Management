package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.model.User;
import com.mbi_re.airport_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Use a default tenant or dynamic tenant based on request
        String tenantId = com.mbi_re.airport_management.config.TenantContext.getTenantId();

        User user = userRepository.findByUsernameAndTenantId(username, tenantId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                java.util.Collections.emptyList() // roles can be added here
        );
    }
}

