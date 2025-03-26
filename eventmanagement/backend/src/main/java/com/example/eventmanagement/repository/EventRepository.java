package com.example.eventmanagement.repository;

import com.example.eventmanagement.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>{
    List<Event> findByCreatorId(Long creatorId);
    List<Event> findByCategory(String category);
    List<Event> findByTitleContainingIgnoreCase(String title);
    List<Event> findByCategoryIgnoreCase(String category);
    List<Event> findByTicketPriceBetween(Double minPrice, Double maxPrice);
    List<Event> findByAvailableSeatsGreaterThan(int seats);
    @Query("SELECT e FROM Event e WHERE e.dateTime > CURRENT_TIMESTAMP AND e.availableSeats > 0")
    List<Event> findUpcomingEvents();
    @Query("SELECT b.event FROM Booking b WHERE b.user.id = :userId")
    List<Event> findUserBookedEvents(@Param("userId") Long userId);
}
