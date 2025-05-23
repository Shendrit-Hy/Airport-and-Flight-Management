package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.dto.UserDTO;
import com.mbi_re.airport_management.model.Country;
import com.mbi_re.airport_management.model.Role;
import com.mbi_re.airport_management.model.User;
import com.mbi_re.airport_management.repository.CountryRepository;
import com.mbi_re.airport_management.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private CountryRepository countryRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        countryRepository = mock(CountryRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);

        userService = new UserService();

        setPrivateField(userService, "userRepository", userRepository);
        setPrivateField(userService, "countryRepository", countryRepository);
        setPrivateField(userService, "passwordEncoder", passwordEncoder);
    }

    @Test
    void testRegisterUser_AssignsAdminIfFirstUser() {
        UserDTO dto = new UserDTO();
        dto.setUsername("admin");
        dto.setFullname("Admin User");
        dto.setPassword("secret");
        dto.setEmail("admin@example.com");
        dto.setCountry("Germany");

        String tenantId = "tenant1";
        when(userRepository.findAllByTenantId(tenantId)).thenReturn(List.of());

        Country country = new Country();
        when(countryRepository.findByNameAndTenantId("Germany", tenantId)).thenReturn(Optional.of(country));
        when(passwordEncoder.encode("secret")).thenReturn("hashed");

        User savedUser = new User();
        savedUser.setUsername("admin");
        savedUser.setRole(Role.ADMIN);
        savedUser.setTenantId(tenantId);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.registerUser(dto, tenantId);

        assertEquals(Role.ADMIN, result.getRole());
        assertEquals("admin", result.getUsername());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("jane");
        when(userRepository.findByUsernameAndTenantId("jane", "tenant1")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername("jane", "tenant1");

        assertTrue(result.isPresent());
        assertEquals("jane", result.get().getUsername());
    }

    @Test
    void testGetUsersByTenant() {
        List<User> mockUsers = List.of(new User(), new User());
        when(userRepository.findAllByTenantId("tenantA")).thenReturn(mockUsers);

        List<User> result = userService.getUsersByTenant("tenantA");

        assertEquals(2, result.size());
    }

    @Test
    void testUpdateUserProfile() {
        try (
                MockedStatic<TenantContext> tenantContextMock = mockStatic(TenantContext.class);
                MockedStatic<SecurityContextHolder> securityContextHolderMock = mockStatic(SecurityContextHolder.class)
        ) {
            tenantContextMock.when(TenantContext::getTenantId).thenReturn("tenantX");

            Authentication authentication = mock(Authentication.class);
            when(authentication.getName()).thenReturn("user1");

            SecurityContext context = mock(SecurityContext.class);
            when(context.getAuthentication()).thenReturn(authentication);
            securityContextHolderMock.when(SecurityContextHolder::getContext).thenReturn(context);

            User user = new User();
            Country country = new Country();
            country.setName("Albania");

            user.setUsername("user1");
            user.setTenantId("tenantX");
            user.setCountry(new Country());

            when(userRepository.findByUsernameAndTenantId("user1", "tenantX")).thenReturn(Optional.of(user));
            when(countryRepository.findByNameAndTenantId("Albania", "tenantX")).thenReturn(Optional.of(country));

            UserDTO updateDTO = new UserDTO();
            updateDTO.setFullname("New Name");
            updateDTO.setEmail("new@email.com");
            updateDTO.setCountry("Albania");
            updateDTO.setPassword("newpass");

            when(passwordEncoder.encode("newpass")).thenReturn("encoded");

            UserDTO result = userService.updateUserProfile(updateDTO);

            assertEquals("New Name", result.getFullname());
            assertEquals("new@email.com", result.getEmail());
        }
    }

    @Test
    void testGetCurrentUserProfile() {
        try (
                MockedStatic<TenantContext> tenantContextMock = mockStatic(TenantContext.class);
                MockedStatic<SecurityContextHolder> securityContextHolderMock = mockStatic(SecurityContextHolder.class)
        ) {
            tenantContextMock.when(TenantContext::getTenantId).thenReturn("tenantY");

            Authentication auth = mock(Authentication.class);
            when(auth.getName()).thenReturn("user42");

            SecurityContext context = mock(SecurityContext.class);
            when(context.getAuthentication()).thenReturn(auth);
            securityContextHolderMock.when(SecurityContextHolder::getContext).thenReturn(context);

            User user = new User();
            user.setUsername("user42");
            user.setTenantId("tenantY");
            user.setFullName("User 42");
            user.setEmail("u42@email.com");
            Country country = new Country();
            country.setName("Kosovo");
            user.setCountry(country);

            when(userRepository.findByUsernameAndTenantId("user42", "tenantY")).thenReturn(Optional.of(user));

            UserDTO profile = userService.getCurrentUserProfile();

            assertEquals("User 42", profile.getFullname());
            assertEquals("Kosovo", profile.getCountry());
        }
    }

    @Test
    void testGetUserById() {
        try (MockedStatic<TenantContext> tenantContextMock = mockStatic(TenantContext.class)) {
            tenantContextMock.when(TenantContext::getTenantId).thenReturn("tenantZ");

            User user = new User();
            user.setId(99L);
            user.setTenantId("tenantZ");

            when(userRepository.findById(99L)).thenReturn(Optional.of(user));

            Optional<User> result = userService.getUserById(99L);

            assertTrue(result.isPresent());
            assertEquals("tenantZ", result.get().getTenantId());
        }
    }

    private void setPrivateField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject field: " + fieldName, e);
        }
    }
}
