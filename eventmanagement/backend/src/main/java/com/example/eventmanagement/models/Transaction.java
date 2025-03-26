package com.example.eventmanagement.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")

public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "transaction_time", nullable = false, updatable = false) 
    private Date transactionTime  = new Date();

    public Transaction(User user, Event event, double amount, String transactionType){
        this.user = user;
        this.event = event;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionTime = new Date();
    }
}
