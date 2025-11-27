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

import java.time.LocalDateTime;

public class EventUseCaseImpl implements ManageEventUseCase {

    private final EventRepositoryPort eventRepositoryPort;
    private final VenueRepositoryPort venueRepositoryPort;

    public EventUseCaseImpl(EventRepositoryPort eventRepositoryPort, VenueRepositoryPort venueRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
        this.venueRepositoryPort = venueRepositoryPort;
    }

    @Override
    public Event createEvent(Event event) {
        // Validacion de negocio: Nombre unico
        if (eventRepositoryPort.existsByName(event.getName())) {
            throw new DuplicateResourceException("Ya existe un evento con el nombre: " + event.getName());
        }

        // Validacion de negocio: Venue debe existir
        // Asumimos que el objeto event viene con un objeto Venue adentro que solo trae el ID
        Long venueId = event.getVenue().getId();
        Venue venue = venueRepositoryPort.findById(venueId)
                .orElseThrow(() -> new ResourceNotFoundException("Venue no encontrado con ID: " + venueId));

        event.setVenue(venue);
        return eventRepositoryPort.save(event);
    }

    @Override
    public Event getEventById(Long id) {
        return eventRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con id: " + id));
    }

    @Override
    public Page<Event> getEvents(String city, String category, LocalDateTime startDate, Pageable pageable) {
        return eventRepositoryPort.findByFilters(city, category, startDate, pageable);
    }

    @Override
    public Event updateEvent(Long id, Event event) {
        Event existingEvent = getEventById(id);

        // Si cambia el nombre, validamos que no exista ya
        if (!existingEvent.getName().equals(event.getName()) && eventRepositoryPort.existsByName(event.getName())) {
            throw new DuplicateResourceException("El nombre ya está en uso");
        }

        Long venueId = event.getVenue().getId();
        Venue venue = venueRepositoryPort.findById(venueId)
                .orElseThrow(() -> new ResourceNotFoundException("Venue no encontrado"));

        event.setId(id);
        event.setVenue(venue);
        return eventRepositoryPort.save(event);
    }

    @Override
    public void deleteEvent(Long id) {
        if (!eventRepositoryPort.existsById(id)) {
            throw new ResourceNotFoundException("Evento no encontrado");
        }
        eventRepositoryPort.deleteById(id);
    }
}