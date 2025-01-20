package com.mukesh.controller;

import com.mukesh.models.AppUser;
import com.mukesh.models.Event;
import com.mukesh.service.ConversationService;
import com.mukesh.service.EventService;
import com.mukesh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {
    private final EventService eventService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private UserService userService;


    public EventController(EventService service) {
        this.eventService = service;
    }

    // Pobieranie wydarzeń dla danego miesiąca
    @GetMapping
    public List<Event> getEventsForMonth(@RequestParam("start") String start, @RequestParam("end") String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return eventService.getEventsForMonth(startDate, endDate);
    }

    // Pobieranie szczegółów wydarzenia po ID
    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestHeader("Authorization") String jwt,
                                             @RequestBody Event event) throws Exception {
        Event createdEvent = eventService.addEvent(event);

        List<Integer> allUserIds = userService.getAllUserIds();

        conversationService.createConversation(
                allUserIds,
                "Chat wydarzenia: " + event.getTitle(),
                true,
                createdEvent.getId()
        );

        return ResponseEntity.ok(createdEvent);
    }


    // Edycja istniejącego wydarzenia
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event updatedEvent) {
        Event event = eventService.updateEvent(id, updatedEvent);
        return ResponseEntity.ok(event);
    }

    // Usuwanie wydarzenia
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<Void> joinEvent(@RequestHeader("Authorization") String jwt, @PathVariable Long id) {
        AppUser user = userService.findUserByJwt(jwt);
        eventService.joinEvent(id, user.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/leave")
    public ResponseEntity<Void> leaveEvent(@RequestHeader("Authorization") String jwt, @PathVariable Long id) {
        AppUser user = userService.findUserByJwt(jwt);
        eventService.leaveEvent(id, user.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<AppUser>> getParticipants(@PathVariable Long id) {
        List<AppUser> participants = eventService.getParticipants(id);
        return ResponseEntity.ok(participants);
    }

}
