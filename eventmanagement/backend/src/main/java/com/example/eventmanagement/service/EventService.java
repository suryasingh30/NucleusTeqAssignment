package com.example.eventmanagement.service;

import com.example.eventmanagement.models.Booking;
import com.example.eventmanagement.models.Event;
import com.example.eventmanagement.models.User;
import com.example.eventmanagement.repository.BookingRepository;
import com.example.eventmanagement.repository.EventRepository;
import com.example.eventmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {
    
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    private EventService(EventRepository eventRepository, UserRepository userRepository, BookingRepository bookingRepository){
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    public Event createEvent(Long userId, Event event){
        User creator = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"));
        event.setCreator(creator);
    return eventRepository.save(event);
    }

    public Event editEvent(Long eventId, Event updatedEvent){
        Event event = eventRepository.findById(eventId)
                        .orElseThrow(() -> new RuntimeException("Event not found"));
        event.setTitle(updatedEvent.getTitle());
        event.setDescription(updatedEvent.getDescription());
        event.setDateTime(updatedEvent.getDateTime());
        event.setTicketPrice(updatedEvent.getTicketPrice());
        event.setAvailableSeats(updatedEvent.getAvailableSeats());
        event.setCategory(updatedEvent.getCategory());
    return eventRepository.save(event);
    }

    public void deleteEvent(Long eventId){
        eventRepository.deleteById(eventId);
    }

    public List<Event> getAllEvent(){
        return eventRepository.findAll();
    }

    public List<Event> getEventsCreator(Long creatorId){
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

    public List<Event> getUserBookedEvents(Long userId) {
        return bookingRepository.findByUserId(userId)
            .stream()
            .map(Booking::getEvent)
            .collect(Collectors.toList());
    }

    public List<Event> getMyEvents(Long userId) {
        return eventRepository.findByCreatorId(userId);
    }
}
