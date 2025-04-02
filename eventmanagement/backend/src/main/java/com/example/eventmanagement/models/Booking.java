package com.example.eventmanagement.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "amount_paid", nullable = false)
    private double amountPaid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "booking_time", nullable = false, updatable = false)
    private Date bookingTime;

    @PrePersist
    protected void onCreate() {
        this.bookingTime = new Date();  // Automatically sets booking time when persisted
    }
}
