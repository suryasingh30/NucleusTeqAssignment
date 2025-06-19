package com.example.eventmanagement.controllers;

import com.example.eventmanagement.models.User;
import com.example.eventmanagement.repository.UserRepository;
import com.example.eventmanagement.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> request) {
        // Register the user and get the JWT token
        String token = authService.register(request.get("name"), request.get("email"), request.get("password"), request.get("role"));
        
        // Find the user to get the user ID
        User user = userRepository.findByEmail(request.get("email")).orElseThrow(() -> new RuntimeException("User not found"));

        // Return a successful response with token and user ID
        return ResponseEntity.ok(Map.of(
            "message", "User registered successfully",
            "token", token,
            "userId", user.getId(),
            "role", user.getRole().toString() // Returning the user's role
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> request) {
        String token = authService.login(request.get("email"), request.get("password"));
        return ResponseEntity.ok(Map.of("message", "Login successful", "token", token));
    }

    @GetMapping("/wallet")
    public ResponseEntity<?> getWalletBalance(@RequestParam Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return ResponseEntity.ok().body("{\"walletBalance\": " + user.getWalletBalance() + "}");
    }
}
