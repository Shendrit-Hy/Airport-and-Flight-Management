package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.UserDTO;
import com.mbi_re.airport_management.model.User;
import com.mbi_re.airport_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(
            @RequestBody UserDTO userDTO,
            @RequestParam String role,
            @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(userService.registerUser(userDTO, role, tenantId));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsersByTenant(@RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(userService.getUsersByTenant(tenantId));
    }
    
    @PutMapping("/profile")
    public ResponseEntity<UserDTO> updateProfile(
            @RequestBody UserDTO updatedUser,
            @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(userService.updateUserProfile(updatedUser, tenantId));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(@RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(userService.getCurrentUserProfile(tenantId));
    }


}