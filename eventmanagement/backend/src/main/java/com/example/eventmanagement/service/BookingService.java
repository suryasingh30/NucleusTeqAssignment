package com.example.eventmanagement.service;

import com.example.eventmanagement.models.*;
import com.example.eventmanagement.repository.BookingRepository;
import com.example.eventmanagement.repository.TransactionRepository;
import com.example.eventmanagement.repository.UserRepository;
import com.example.eventmanagement.repository.EventRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final TransactionRepository transactionRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository,EventRepository eventRepository, TransactionRepository transactionRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public String bookEvent(Long eventId, Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        if (event.getAvailableSeats() <= 0) {
            return "No seats available for this event!";
        }

        if (user.getWalletBalance() < event.getTicketPrice()) {
            return "Insufficient balance in Wallet!";
        }

        user.setWalletBalance(user.getWalletBalance() - event.getTicketPrice());
        userRepository.save(user);

        User eventCreator = event.getCreator();
        eventCreator.setWalletBalance(eventCreator.getWalletBalance() + event.getTicketPrice());
        userRepository.save(eventCreator);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setEvent(event);
        booking.setAmountPaid(event.getTicketPrice()); 
        bookingRepository.save(booking);

        Transaction debitTransaction = new Transaction(user, event, -event.getTicketPrice(), "DEBIT");
        Transaction creditTransaction = new Transaction(eventCreator, event, event.getTicketPrice(), "CREDIT");

        transactionRepository.save(debitTransaction);
        transactionRepository.save(creditTransaction);

        event.setAvailableSeats(event.getAvailableSeats() - 1);
        eventRepository.save(event);

        return "Booking Successful!";
    }

    @Transactional
    public String cancelBooking(Long eventId, Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Booking booking = bookingRepository.findByUserIdAndEventId(user.getId(), eventId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found!"));

        Event event = booking.getEvent();
        User eventCreator = event.getCreator();

        user.setWalletBalance(user.getWalletBalance() + booking.getAmountPaid());
        userRepository.save(user);

        eventCreator.setWalletBalance(eventCreator.getWalletBalance() - booking.getAmountPaid());
        userRepository.save(eventCreator);

        event.setAvailableSeats(event.getAvailableSeats() + 1);
        eventRepository.save(event);

        Transaction refundTransaction = new Transaction(user, event, booking.getAmountPaid(), "CREDIT");
        transactionRepository.save(refundTransaction);

        bookingRepository.delete(booking);

        return "Booking successfully cancelled. Amount refunded!";
    }

    public List<Booking> getUserBookings(Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return bookingRepository.findByUserId(user.getId());
    }

    public boolean isEventBooked(String userEmail, Long eventId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return bookingRepository.findByUserIdAndEventId(user.getId(), eventId).isPresent();
    }
    
}
