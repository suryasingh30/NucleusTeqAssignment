package com.example.eventmanagement.controllers;

import com.example.eventmanagement.models.Booking;
import com.example.eventmanagement.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PreAuthorize("hasRole('ATTENDEE')")
    @PostMapping("/book")
    public ResponseEntity<String> bookEvent(@RequestParam Long eventId, Authentication authentication) {
        String message = bookingService.bookEvent(eventId, authentication);
        return ResponseEntity.ok(message);
    }

    @PreAuthorize("hasRole('ATTENDEE')")
    @PostMapping("/cancel")
    public ResponseEntity<String> cancelBooking(@RequestParam Long eventId, Authentication authentication) {
        String message = bookingService.cancelBooking(eventId, authentication);
        return ResponseEntity.ok(message);
    }

    @PreAuthorize("hasRole('ATTENDEE')")
    @GetMapping("/user")
    public ResponseEntity<List<Booking>> getUserBookings(Authentication authentication) {
        List<Booking> userBookings = bookingService.getUserBookings(authentication);
        return ResponseEntity.ok(userBookings);
    }

    @PreAuthorize("hasRole('ATTENDEE')")
@GetMapping("/isBooked")
public ResponseEntity<Boolean> isEventBooked(@RequestParam Long eventId, Authentication authentication) {
    String userEmail = authentication.getName();
    boolean isBooked = bookingService.isEventBooked(userEmail, eventId);
    return ResponseEntity.ok(isBooked);
}

}
