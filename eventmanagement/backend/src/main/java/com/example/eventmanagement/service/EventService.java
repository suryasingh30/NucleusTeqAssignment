package com.example.eventmanagement.service;

import com.example.eventmanagement.models.*;
import com.example.eventmanagement.repository.BookingRepository;
import com.example.eventmanagement.repository.EventRepository;
import com.example.eventmanagement.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {
    
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository, BookingRepository bookingRepository){
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    @PreAuthorize("hasRole('ORGANIZER')")
    public Event createEvent(Long userId, Event event){
        User creator = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"));
        event.setCreator(creator);
        return eventRepository.save(event);
    }

    @PreAuthorize("hasRole('ORGANIZER')")
    public Event editEvent(Long eventId, Event updatedEvent) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = authentication.getName();
        
        if (!event.getCreator().getEmail().equals(loggedInEmail)) {
            throw new RuntimeException("You are not authorized to edit this event.");
        }
    
        event.setTitle(updatedEvent.getTitle());
        event.setDescription(updatedEvent.getDescription());
        event.setDateTime(updatedEvent.getDateTime());
        event.setTicketPrice(updatedEvent.getTicketPrice());
        event.setAvailableSeats(updatedEvent.getAvailableSeats());
        event.setCategory(updatedEvent.getCategory());
    
        return eventRepository.save(event);
    }

    @PreAuthorize("hasRole('ORGANIZER')")
    public void deleteEvent(Long eventId){
        eventRepository.deleteById(eventId);
    }

    public List<Event> getAllEvents(){
        return eventRepository.findAll();
    }

    @PreAuthorize("hasRole('ORGANIZER')")
    public List<Event> getMyEvents(Long creatorId){
        return eventRepository.findByCreatorId(creatorId);
    }

    public Optional<Event> getEventById(Long eventId){
        return eventRepository.findById(eventId);
    }

    public List<Event> searchEvents(String title, String category, Double minPrice, Double maxPrice, Boolean availableOnly) {
        if (title != null && !title.isEmpty()) {
            return eventRepository.findByTitleContainingIgnoreCase(title);
        }
        
        if (category != null && !category.isEmpty()) {
            return eventRepository.findByCategoryIgnoreCase(category);
        }
    
        if (minPrice != null && maxPrice != null) {
            return eventRepository.findByTicketPriceBetween(minPrice, maxPrice);
        }
    
        if (availableOnly != null && availableOnly) {
            return eventRepository.findByAvailableSeatsGreaterThan(0);
        }
    
        return eventRepository.findAll();
    }

    public List<Event> getUpcomingEvents() {
        return eventRepository.findUpcomingEvents();
    }

    public List<Event> getAllEvent(){
        return eventRepository.findAll();
    }

    @PreAuthorize("hasRole('ATTENDEE')")
    public List<Event> getUserBookedEvents(Long userId) {
        return bookingRepository.findByUserId(userId)
            .stream()
            .map(Booking::getEvent)
            .collect(Collectors.toList());
    }
}
