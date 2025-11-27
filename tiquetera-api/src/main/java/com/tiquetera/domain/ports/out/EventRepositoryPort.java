package com.tiquetera.domain.ports.out;

import com.tiquetera.domain.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.Optional;

public interface EventRepositoryPort {
    Event save(Event event);
    Optional<Event> findById(Long id);
    Page<Event> findByFilters(String city, String category, LocalDateTime startDate, Pageable pageable);
    void deleteById(Long id);
    boolean existsByName(String name);
    boolean existsById(Long id);
}