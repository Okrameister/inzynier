package com.mukesh.service;

import com.mukesh.models.AppUser;
import com.mukesh.models.Event;
import com.mukesh.repository.EventRepository;
import com.mukesh.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {
    private final EventRepository repository;
    private final UserRepository userRepository;

    public EventService(EventRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<Event> getEventsForMonth(LocalDate start, LocalDate end) {
        return repository.findAllByDateBetween(start, end);
    }

    public Event getEventById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public Event addEvent(Event event) {
        return repository.save(event);
    }

    public void joinEvent(Long eventId, Integer userId) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        event.getParticipants().add(user);
        repository.save(event);
    }

    public void leaveEvent(Long eventId, Integer userId) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        event.getParticipants().remove(user);
        repository.save(event);
    }

    public List<AppUser> getParticipants(Long eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return List.copyOf(event.getParticipants());
    }

    public Event updateEvent(Long id, Event updatedEvent) {
        Event existingEvent = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        existingEvent.setTitle(updatedEvent.getTitle());
        existingEvent.setDescription(updatedEvent.getDescription());
        existingEvent.setDate(updatedEvent.getDate());

        return repository.save(existingEvent);
    }

    public void deleteEvent(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Event not found");
        }
        repository.deleteById(id);
    }
}
