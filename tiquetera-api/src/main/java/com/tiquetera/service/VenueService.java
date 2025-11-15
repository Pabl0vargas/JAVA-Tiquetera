package com.tiquetera.service;

import com.tiquetera.dto.VenueDTO;
import com.tiquetera.exception.ResourceNotFoundException;
import com.tiquetera.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {

    @Autowired
    private VenueRepository venueRepository;

    public List<VenueDTO> getAllVenues() {
        return venueRepository.findAll();
    }

    public VenueDTO getVenueById(Long id) {
        return venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue no encontrado con id: " + id));
    }

    public VenueDTO createVenue(VenueDTO venue) {
        // Aquí iría lógica de negocio, ej. validar si ya existe
        return venueRepository.save(venue);
    }

    public VenueDTO updateVenue(Long id, VenueDTO venueDetails) {
        VenueDTO venue = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue no encontrado con id: " + id));

        // Actualizamos los campos
        venue.setName(venueDetails.getName());
        venue.setAddress(venueDetails.getAddress());
        venue.setCapacity(venueDetails.getCapacity());

        return venueRepository.save(venue);
    }

    public void deleteVenue(Long id) {
        VenueDTO venue = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue no encontrado con id: " + id));
        venueRepository.deleteById(venue.getId());
    }
}