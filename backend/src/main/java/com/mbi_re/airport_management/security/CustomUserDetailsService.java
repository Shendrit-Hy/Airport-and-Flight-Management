package com.mbi_re.airport_management.security;

import com.mbi_re.airport_management.model.User;
import com.mbi_re.airport_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Custom implementation of user details retrieval for Spring Security.
 * <p>
 * This service loads a user's credentials and roles based on the username
 * and tenant ID for multi-tenant support.
 */
@Service
public class CustomUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Loads the user by username and tenant ID.
     * <p>
     * This method fetches a {@link User} from the database and constructs
     * a {@link UserDetails} object for Spring Security authentication.
     *
     * @param username the username of the user
     * @param tenantId the tenant ID to which the user belongs
     * @return the {@link UserDetails} for authentication
     * @throws UsernameNotFoundException if the user is not found for the given username and tenant ID
     */
    public UserDetails loadUserByUsernameAndTenant(String username, String tenantId) {
        User user = userRepository.findByUsernameAndTenantId(username, tenantId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())))
                .build();
    }
}
