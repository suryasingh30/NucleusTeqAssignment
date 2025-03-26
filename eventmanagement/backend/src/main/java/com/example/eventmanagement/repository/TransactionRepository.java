package com.example.eventmanagement.repository;

import com.example.eventmanagement.models.Transaction;
import com.example.eventmanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
}
