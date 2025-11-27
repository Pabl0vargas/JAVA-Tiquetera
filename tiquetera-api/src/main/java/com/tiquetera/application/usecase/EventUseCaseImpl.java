package com.tiquetera.application.usecase;

import com.tiquetera.domain.model.Event;
import com.tiquetera.domain.model.Venue;
import com.tiquetera.domain.ports.in.ManageEventUseCase;
import com.tiquetera.domain.ports.out.EventRepositoryPort;
import com.tiquetera.domain.ports.out.VenueRepositoryPort;
import com.tiquetera.exception.DuplicateResourceException;
import com.tiquetera.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional; // Importación correcta

import java.time.LocalDateTime;

public class EventUseCaseImpl implements ManageEventUseCase {

    private final EventRepositoryPort eventRepositoryPort;
    private final VenueRepositoryPort venueRepositoryPort;

    public EventUseCaseImpl(EventRepositoryPort eventRepositoryPort, VenueRepositoryPort venueRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
        this.venueRepositoryPort = venueRepositoryPort;
    }

    @Override
    @Transactional // Si algo falla, hace rollback automático
    public Event createEvent(Event event) {
        if (eventRepositoryPort.existsByName(event.getName())) {
            throw new DuplicateResourceException("Ya existe un evento con el nombre: " + event.getName());
        }

        Venue venue = venueRepositoryPort.findById(event.getVenue().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue no encontrado con ID: " + event.getVenue().getId()));

        event.setVenue(venue);
        return eventRepositoryPort.save(event);
    }

    @Override
    @Transactional(readOnly = true) // Optimización de lectura
    public Event getEventById(Long id) {
        return eventRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Event> getEvents(String city, String category, LocalDateTime startDate, Pageable pageable) {
        return eventRepositoryPort.findByFilters(city, category, startDate, pageable);
    }

    @Override
    @Transactional
    public Event updateEvent(Long id, Event event) {
        Event existingEvent = getEventById(id);

        if (!existingEvent.getName().equals(event.getName()) && eventRepositoryPort.existsByName(event.getName())) {
            throw new DuplicateResourceException("El nombre ya está en uso");
        }

        Venue venue = venueRepositoryPort.findById(event.getVenue().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue no encontrado"));

        event.setId(id);
        event.setVenue(venue);
        return eventRepositoryPort.save(event);
    }

    @Override
    @Transactional
    public void deleteEvent(Long id) {
        if (!eventRepositoryPort.existsById(id)) {
            throw new ResourceNotFoundException("Evento no encontrado");
        }
        eventRepositoryPort.deleteById(id);
    }
}