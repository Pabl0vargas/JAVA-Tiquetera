package com.tiquetera.application.usecase;

import com.tiquetera.domain.model.Venue;
import com.tiquetera.domain.ports.in.ManageVenueUseCase;
import com.tiquetera.domain.ports.out.VenueRepositoryPort;
import com.tiquetera.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class VenueUseCaseImpl implements ManageVenueUseCase {

    private final VenueRepositoryPort venueRepositoryPort;

    public VenueUseCaseImpl(VenueRepositoryPort venueRepositoryPort) {
        this.venueRepositoryPort = venueRepositoryPort;
    }

    @Override
    @Transactional
    public Venue createVenue(Venue venue) {
        return venueRepositoryPort.save(venue);
    }

    @Override
    @Transactional(readOnly = true)
    public Venue getVenueById(Long id) {
        return venueRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue no encontrado con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venue> getAllVenues() {
        return venueRepositoryPort.findAll();
    }

    @Override
    @Transactional
    public Venue updateVenue(Long id, Venue venue) {
        if (!venueRepositoryPort.existsById(id)) {
            throw new ResourceNotFoundException("Venue no encontrado con id: " + id);
        }
        venue.setId(id);
        return venueRepositoryPort.save(venue);
    }

    @Override
    @Transactional
    public void deleteVenue(Long id) {
        if (!venueRepositoryPort.existsById(id)) {
            throw new ResourceNotFoundException("Venue no encontrado con id: " + id);
        }
        venueRepositoryPort.deleteById(id);
    }
}