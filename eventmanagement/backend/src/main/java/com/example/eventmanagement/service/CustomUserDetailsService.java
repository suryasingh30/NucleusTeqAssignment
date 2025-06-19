package com.example.eventmanagement.service;

import com.example.eventmanagement.models.User;
import com.example.eventmanagement.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> userOptional = userRepository.findByEmail(email);
    if (userOptional.isEmpty()) {
        throw new UsernameNotFoundException("User not found with email: " + email);
    }

    User user = userOptional.get();
    String role = "ROLE_" + user.getRole().name(); // Add ROLE_ prefix

    return org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .roles(role) // Dynamically assign the correct role with ROLE_ prefix
            .build();
}

}
