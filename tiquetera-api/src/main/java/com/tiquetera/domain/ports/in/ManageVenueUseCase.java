package com.tiquetera.domain.ports.in;

import com.tiquetera.domain.model.Venue;
import java.util.List;

public interface ManageVenueUseCase {
    Venue createVenue(Venue venue);
    Venue getVenueById(Long id);
    List<Venue> getAllVenues();
    Venue updateVenue(Long id, Venue venue);
    void deleteVenue(Long id);
}