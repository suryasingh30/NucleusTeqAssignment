package com.example.eventmanagement.service;

import com.example.eventmanagement.models.User;
import com.example.eventmanagement.repository.UserRepository;
import com.example.eventmanagement.config.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String register(String name, String email, String password, String role){
        
        // Check if the email already exists
        Optional<User> existingUser = userRepository.findByEmail(email);
        if(existingUser.isPresent())
            throw new RuntimeException("Email already exists!");

        // Create a new user object and set its details
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setWalletBalance(1000.0);
        user.setRole(User.UserRole.valueOf(role.toUpperCase()));
        userRepository.save(user);

        // Generate and return the JWT token
        return jwtUtil.generateToken(user.getEmail(), List.of(user.getRole().toString()));
    }

    public String login(String email, String password){

        // Find the user by email
        User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not Found"));

        // Verify the password
        if(!passwordEncoder.matches(password, user.getPassword()))
            throw new RuntimeException("Invalid Password");
        
        // Generate and return the JWT token
        return jwtUtil.generateToken(user.getEmail(), List.of(user.getRole().toString()));
    }
}
