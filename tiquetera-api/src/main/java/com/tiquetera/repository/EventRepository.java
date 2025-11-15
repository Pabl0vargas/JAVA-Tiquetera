package com.tiquetera.repository;

import com.tiquetera.dto.EventDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EventRepository {

    private final Map<Long, EventDTO> eventStore = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    public List<EventDTO> findAll() {
        return new ArrayList<>(eventStore.values());
    }

    public Optional<EventDTO> findById(Long id) {
        return Optional.ofNullable(eventStore.get(id));
    }

    public EventDTO save(EventDTO event) {
        if (event.getId() == null) {
            event.setId(idCounter.incrementAndGet());
        }
        eventStore.put(event.getId(), event);
        return event;
    }

    public void deleteById(Long id) {
        eventStore.remove(id);
    }
}