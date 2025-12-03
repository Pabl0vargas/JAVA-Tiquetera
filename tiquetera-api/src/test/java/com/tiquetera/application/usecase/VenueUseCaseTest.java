package com.tiquetera.application.usecase;

import com.tiquetera.domain.model.Venue;
import com.tiquetera.domain.ports.out.VenueRepositoryPort;
import com.tiquetera.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VenueUseCaseTest {

    @Mock
    private VenueRepositoryPort venueRepositoryPort;

    @InjectMocks
    private VenueUseCaseImpl venueUseCase;

    @Test
    void createVenue_ShouldReturnVenue() {
        Venue venue = new Venue();
        venue.setName("Arena Test");
        when(venueRepositoryPort.save(any(Venue.class))).thenReturn(venue);

        Venue result = venueUseCase.createVenue(venue);

        assertNotNull(result);
        assertEquals("Arena Test", result.getName());
    }

    @Test
    void getVenueById_ShouldThrowException_WhenNotFound() {
        when(venueRepositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> venueUseCase.getVenueById(99L));
    }

    @Test
    void getAllVenues_ShouldReturnList() {
        when(venueRepositoryPort.findAll()).thenReturn(List.of(new Venue(), new Venue()));

        List<Venue> result = venueUseCase.getAllVenues();

        assertEquals(2, result.size());
    }
}