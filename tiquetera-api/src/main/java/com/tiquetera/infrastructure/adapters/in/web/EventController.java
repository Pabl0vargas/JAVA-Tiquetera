package com.tiquetera.infrastructure.adapters.in.web;

import com.tiquetera.domain.model.Event;
import com.tiquetera.domain.model.Venue;
import com.tiquetera.domain.ports.in.ManageEventUseCase;
import com.tiquetera.infrastructure.adapters.in.web.dto.EventDTO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Tag(name = "Gestión de Eventos (Hexagonal)")
public class EventController {

    private final ManageEventUseCase manageEventUseCase;

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO dto) {
        Event event = mapToDomain(dto);
        Event created = manageEventUseCase.createEvent(event);
        return new ResponseEntity<>(mapToDto(created), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<EventDTO>> getAllEvents(
            @Parameter(description = "Filtrar por ciudad") @RequestParam(required = false) String city,
            @Parameter(description = "Filtrar por categoría") @RequestParam(required = false) String category,
            @Parameter(description = "Filtrar por fecha") @RequestParam(required = false) LocalDateTime startDate,
            Pageable pageable
    ) {
        return ResponseEntity.ok(manageEventUseCase.getEvents(city, category, startDate, pageable)
                .map(this::mapToDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(mapToDto(manageEventUseCase.getEventById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @Valid @RequestBody EventDTO dto) {
        Event updated = manageEventUseCase.updateEvent(id, mapToDomain(dto));
        return ResponseEntity.ok(mapToDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        manageEventUseCase.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    // Mappers Manuales simples
    private Event mapToDomain(EventDTO dto) {
        Event event = new Event();
        event.setName(dto.getName());
        event.setEventDate(dto.getEventDate());
        event.setCategory(dto.getCategory());
        Venue venue = new Venue();
        venue.setId(dto.getVenueId());
        event.setVenue(venue);
        return event;
    }

    private EventDTO mapToDto(Event domain) {
        EventDTO dto = new EventDTO();
        dto.setId(domain.getId());
        dto.setName(domain.getName());
        dto.setEventDate(domain.getEventDate());
        dto.setCategory(domain.getCategory());
        if (domain.getVenue() != null) dto.setVenueId(domain.getVenue().getId());
        return dto;
    }
}