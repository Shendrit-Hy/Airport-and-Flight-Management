package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.UserDTO;
import com.mbi_re.airport_management.model.User;
import com.mbi_re.airport_management.security.JwtService;
import com.mbi_re.airport_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<User> register(
            @RequestBody UserDTO userDTO,
            @RequestHeader(value = "X-Tenant-ID", required = false) String tenantId) {
        System.out.println("Received tenant ID: " + tenantId);  // Log to verify
        if (tenantId == null || tenantId.isEmpty()) {
            return ResponseEntity.badRequest().body(null); // You can return an error if the tenantId is not present
        }
        return ResponseEntity.ok(userService.registerUser(userDTO, tenantId));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(@RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(userService.getCurrentUserProfile(tenantId));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(
            @PathVariable Long id,
            @RequestHeader("X-Tenant-ID") String tenantId) {
        Optional<User> user = userService.getUserById(id, tenantId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDTO> updateProfile(
            @RequestBody UserDTO updatedUser,
            @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(userService.updateUserProfile(updatedUser, tenantId));
    }

}
