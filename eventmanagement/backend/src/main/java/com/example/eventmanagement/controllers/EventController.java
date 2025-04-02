package com.example.eventmanagement.controllers;

import com.example.eventmanagement.models.Event;
import com.example.eventmanagement.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/events")
public class EventController {
    
    private final EventService eventService;

    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @PreAuthorize("hasRole('ORGANIZER')")
    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@RequestParam Long userId, @RequestBody Event event){
        Event createdEvent = eventService.createEvent(userId, event);
        return ResponseEntity.ok(createdEvent);
    }

    @PreAuthorize("hasRole('ORGANIZER')")
    @PutMapping("/edit/{eventId}")
public ResponseEntity<?> editEvent(@PathVariable Long eventId, @RequestBody Event updatedEvent) {
    System.out.println("Received eventId: " + eventId);
    System.out.println("Received updatedEvent: " + updatedEvent);

    Event event = eventService.editEvent(eventId, updatedEvent);
    return ResponseEntity.ok(event);
}

    @PreAuthorize("hasRole('ORGANIZER')")
    @DeleteMapping("delete/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long eventId){
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok("Event deleted Successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Event>> getAllEvents(){
        return ResponseEntity.ok(eventService.getAllEvent());
    }

    @GetMapping("/user/{eventId}")
    public ResponseEntity<?> getEventById(@PathVariable Long eventId){
        Optional<Event> event = eventService.getEventById(eventId);
        return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Event>> searchEvents(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Boolean availableOnly) {
        
        List<Event> events = eventService.searchEvents(title, category, minPrice, maxPrice, availableOnly);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Event>> getUpcomingEvents() {
        List<Event> events = eventService.getUpcomingEvents();
        return ResponseEntity.ok(events);
    }

    @PreAuthorize("hasRole('ATTENDEE')")  
    @GetMapping("/user-booked")
    public ResponseEntity<List<Event>> getUserBookedEvents(@RequestParam Long userId) {
        List<Event> bookedEvents = eventService.getUserBookedEvents(userId);
        return ResponseEntity.ok(bookedEvents);
    }

    @PreAuthorize("hasRole('ORGANIZER')") 
    @GetMapping("/my-events")
    public ResponseEntity<List<Event>> getMyEvents(@RequestParam Long userId) {
        List<Event> myEvents = eventService.getMyEvents(userId);
        return ResponseEntity.ok(myEvents);
    }
}
