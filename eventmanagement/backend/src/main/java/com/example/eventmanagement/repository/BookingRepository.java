package com.example.eventmanagement.repository;

import com.example.eventmanagement.models.Booking;
import com.example.eventmanagement.models.Event;
import com.example.eventmanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
    List<Booking> findByEvent(Event event);
    Optional<Booking> findByUserIdAndEventId(Long userId, Long eventId);
    List<Booking> findByUserId(Long userId);
}
