package com.tiquetera.application.usecase;

import com.tiquetera.domain.model.Event;
import com.tiquetera.domain.model.Venue;
import com.tiquetera.domain.ports.out.EventRepositoryPort;
import com.tiquetera.domain.ports.out.VenueRepositoryPort;
import com.tiquetera.exception.DuplicateResourceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventUseCaseTest {

    @Mock
    private EventRepositoryPort eventRepositoryPort;

    @Mock
    private VenueRepositoryPort venueRepositoryPort;

    @InjectMocks
    private EventUseCaseImpl eventUseCase;

    @Test
    void createEvent_ShouldSucceed_WhenDataIsValid() {
        // Arrange
        Venue venue = new Venue();
        venue.setId(1L);

        Event event = new Event();
        event.setName("Concierto Test");
        event.setEventDate(LocalDateTime.now().plusDays(1));
        event.setVenue(venue);

        when(eventRepositoryPort.existsByName(any())).thenReturn(false);
        when(venueRepositoryPort.findById(1L)).thenReturn(Optional.of(venue));
        when(eventRepositoryPort.save(any(Event.class))).thenReturn(event);

        // Act
        Event created = eventUseCase.createEvent(event);

        // Assert
        assertNotNull(created);
        assertEquals("Concierto Test", created.getName());
        verify(eventRepositoryPort).save(any(Event.class));
    }

    @Test
    void createEvent_ShouldThrowException_WhenNameExists() {
        // Arrange
        Event event = new Event();
        event.setName("Duplicado");

        when(eventRepositoryPort.existsByName("Duplicado")).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateResourceException.class, () -> eventUseCase.createEvent(event));
        verify(eventRepositoryPort, never()).save(any());
    }
}