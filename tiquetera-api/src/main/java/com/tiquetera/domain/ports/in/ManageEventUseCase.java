package com.tiquetera.domain.ports.in;

import com.tiquetera.domain.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

public interface ManageEventUseCase {
    Event createEvent(Event event);
    Event getEventById(Long id);
    Page<Event> getEvents(String city, String category, LocalDateTime startDate, Pageable pageable);
    Event updateEvent(Long id, Event event);
    void deleteEvent(Long id);
}