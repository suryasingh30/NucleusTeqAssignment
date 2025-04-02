package com.example.eventmanagement.models;

import jakarta.persistence.*;


@Entity
@Table(name = "users") 
public class User {
    public enum UserRole {
        ORGANIZER, ATTENDEE;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private double walletBalance = 1000.0;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public double getWalletBalance() { return walletBalance; }
    public void setWalletBalance(double walletBalance) { this.walletBalance = walletBalance; }

    public UserRole getRole() { 
        return role; 
    }

    public void setRole(UserRole role) { 
        this.role = role; 
    }
}
