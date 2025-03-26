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
    private  Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "amount_paid", nullable = false)
    private double amount_paid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "booking_time", nullable = false, updatable = false)
    private Date bookingTime = new Date();
    
    public double getAmountPaid(){
        return amount_paid;
    }

    public void setAmountPaid(double amount_paid) {
        this.amount_paid = amount_paid;
    }
}