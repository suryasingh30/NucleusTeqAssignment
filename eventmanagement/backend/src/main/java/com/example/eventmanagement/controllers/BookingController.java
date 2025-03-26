package com.example.eventmanagement.controllers;

import com.example.eventmanagement.models.Booking;
import com.example.eventmanagement.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public ResponseEntity<String> bookEvent(@RequestParam Long userId, @RequestParam Long eventId) {
        String message = bookingService.bookEvent(userId, eventId);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelBooking(@RequestParam Long userId, @RequestParam Long eventId) {
        String message = bookingService.cancelBooking(userId, eventId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Booking>> getUserBookings(@RequestParam Long userId, Authentication authentication) {
        String authenticatedUserEmail = authentication.getName();
        List<Booking> userBookings = bookingService.getUserBookings(userId, authenticatedUserEmail);
        return ResponseEntity.ok(userBookings);
    }
}
