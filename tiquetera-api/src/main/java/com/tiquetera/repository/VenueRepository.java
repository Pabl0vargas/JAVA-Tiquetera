package com.tiquetera.repository;

import com.tiquetera.dto.VenueDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class VenueRepository {

    // Usamos ConcurrentHashMap para seguridad en concurrencia y AtomicLong para IDs
    private final Map<Long, VenueDTO> venueStore = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    public List<VenueDTO> findAll() {
        return new ArrayList<>(venueStore.values());
    }

    public Optional<VenueDTO> findById(Long id) {
        return Optional.ofNullable(venueStore.get(id));
    }

    public VenueDTO save(VenueDTO venue) {
        if (venue.getId() == null) {
            venue.setId(idCounter.incrementAndGet());
        }
        venueStore.put(venue.getId(), venue);
        return venue;
    }

    public void deleteById(Long id) {
        venueStore.remove(id);
    }
}