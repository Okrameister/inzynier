package com.mukesh.repository;

import com.mukesh.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByDateBetween(LocalDate start, LocalDate end);
}
