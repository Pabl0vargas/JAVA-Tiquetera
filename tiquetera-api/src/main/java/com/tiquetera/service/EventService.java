package com.tiquetera.service;

import com.tiquetera.dto.EventDTO;
import com.tiquetera.exception.ResourceNotFoundException;
import com.tiquetera.repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // Opcional: Descomenta para validar que el Venue existe al crear/actualizar
    // @Autowired
    // private VenueRepository venueRepository;

    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll();
    }

    public EventDTO getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con id: " + id));
    }

    public EventDTO createEvent(EventDTO event) {

        // Long venueId = event.getVenueId();
        // venueRepository.findById(venueId)
        //         .orElseThrow(() -> new ResourceNotFoundException("Venue no encontrado con id: " + venueId));

        return eventRepository.save(event);
    }

    public EventDTO updateEvent(Long id, EventDTO eventDetails) {
        EventDTO event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con id: " + id));

        // Validación opcional del Venue
        // Long venueId = eventDetails.getVenueId();
        // venueRepository.findById(venueId)
        //         .orElseThrow(() -> new ResourceNotFoundException("Venue no encontrado con id: " + venueId));

        // Actualizamos los campos
        event.setName(eventDetails.getName());
        event.setEventDate(eventDetails.getEventDate());
        event.setVenueId(eventDetails.getVenueId());

        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        EventDTO event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con id: " + id));
        eventRepository.deleteById(event.getId());
    }
}