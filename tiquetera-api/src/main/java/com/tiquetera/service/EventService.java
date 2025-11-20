package com.tiquetera.service;

import com.tiquetera.dto.EventDTO;
import com.tiquetera.entity.EventEntity;
import com.tiquetera.entity.VenueEntity;
import com.tiquetera.exception.DuplicateResourceException;
import com.tiquetera.exception.ResourceNotFoundException;
import com.tiquetera.repository.EventRepository;
import com.tiquetera.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private VenueRepository venueRepository;

    // 1. Paginación y Filtros
    public Page<EventDTO> getAllEvents(String city, String category, LocalDateTime startDate, Pageable pageable) {
        Page<EventEntity> eventPage = eventRepository.findByFilters(city, category, startDate, pageable);
        return eventPage.map(this::convertToDTO);
    }

    public EventDTO getEventById(Long id) {
        EventEntity entity = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con id: " + id));
        return convertToDTO(entity);
    }

    public EventDTO createEvent(EventDTO dto) {
        // Validar nombre duplicado
        if (eventRepository.existsByName(dto.getName())) {
            throw new DuplicateResourceException("Ya existe un evento con el nombre: " + dto.getName());
        }

        // Validar existencia del Venue
        VenueEntity venue = venueRepository.findById(dto.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue no encontrado con id: " + dto.getVenueId()));

        EventEntity entity = new EventEntity();
        entity.setName(dto.getName());
        entity.setEventDate(dto.getEventDate());
        entity.setCategory(dto.getCategory());
        entity.setVenue(venue);

        return convertToDTO(eventRepository.save(entity));
    }

    public EventDTO updateEvent(Long id, EventDTO dto) {
        EventEntity entity = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado"));

        // Validar duplicado solo si el nombre cambia
        if (!entity.getName().equals(dto.getName()) && eventRepository.existsByName(dto.getName())) {
            throw new DuplicateResourceException("El nombre ya está en uso por otro evento");
        }

        VenueEntity venue = venueRepository.findById(dto.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue no encontrado"));

        entity.setName(dto.getName());
        entity.setEventDate(dto.getEventDate());
        entity.setCategory(dto.getCategory());
        entity.setVenue(venue);

        return convertToDTO(eventRepository.save(entity));
    }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Evento no encontrado");
        }
        eventRepository.deleteById(id);
    }

    private EventDTO convertToDTO(EventEntity entity) {
        EventDTO dto = new EventDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEventDate(entity.getEventDate());
        dto.setCategory(entity.getCategory());
        dto.setVenueId(entity.getVenue().getId());
        return dto;
    }
}