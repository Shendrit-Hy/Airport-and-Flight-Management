package com.mbi_re.airport_management.config;

import com.mbi_re.airport_management.security.JwtAuthenticationEntryPoint;
import com.mbi_re.airport_management.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Configuration class for Spring Security.
 * Sets up password encoding, authentication management, CORS,
 * and HTTP security with JWT-based authentication.
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Provides a PasswordEncoder bean using BCrypt hashing algorithm.
     * Used to encode and verify user passwords securely.
     *
     * @return a BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Exposes the AuthenticationManager bean from the AuthenticationConfiguration.
     * Enables authentication support for the application.
     *
     * @param config the AuthenticationConfiguration provided by Spring Security
     * @return the AuthenticationManager instance
     * @throws Exception if the AuthenticationManager cannot be created
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configures CORS settings to allow requests from specific frontend origins,
     * permitted HTTP methods, allowed headers, and supports credentials.
     *
     * @return a CorsConfigurationSource containing CORS configuration
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:5179", "http://localhost:5173")); // Frontend URLs
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Tenant-ID"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    /**
     * Configures the main HTTP security filter chain for the application.
     * Disables CSRF protection, enables CORS, sets session management to stateless,
     * configures exception handling for unauthorized requests,
     * specifies authorization rules for endpoints,
     * and adds JWT authentication filter before UsernamePasswordAuthenticationFilter.
     *
     * @param http the HttpSecurity to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if there is an error during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login", "/api/auth/register", "/api/auth/users").permitAll()
                        .requestMatchers("/api/auth/profile").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/flights").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/support").permitAll()
                        .requestMatchers("/api/payments").permitAll()
                        .requestMatchers("/api/staff/all").hasRole("ADMIN")
                        .requestMatchers("/api/flights/filter").permitAll()
                        .requestMatchers("/api/announcements").permitAll()
                        .requestMatchers("/api/faqs").permitAll()
                        .requestMatchers("/api/airlines", "/api/airlines/**").permitAll()
                        .requestMatchers("/api/countries").permitAll()
                        .requestMatchers("/api/terminals", "/api/terminals/**").permitAll()
                        .requestMatchers("/api/gates", "/api/gates/**").permitAll()
                        .requestMatchers("/api/seats/available/**").permitAll()
                        .requestMatchers("/api/currencies").permitAll()
                        .requestMatchers("/api/passengers").permitAll()
                        .requestMatchers("/api/bookings").permitAll()
                        .requestMatchers("/api/timezones").permitAll()
                        .requestMatchers("/api/cities").permitAll()
                        .requestMatchers("/api/languages").permitAll()
                        .requestMatchers("/api/trending-places", "/api/trending-places/**").permitAll()
                        .requestMatchers("/api/languages/**").permitAll()
                        .requestMatchers("/api/policies", "/api/policies/**").permitAll()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
