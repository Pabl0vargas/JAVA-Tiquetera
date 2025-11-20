package com.tiquetera.controller;

import com.tiquetera.dto.EventDTO;
import com.tiquetera.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/events")
@Tag(name = "Gestión de Eventos", description = "API para el CRUD de eventos con persistencia")
public class EventController {

    @Autowired
    private EventService eventService;

    @Operation(summary = "Crear un nuevo evento",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Creado"),
                    @ApiResponse(responseCode = "409", description = "Conflicto: Nombre duplicado")
            })
    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO event) {
        EventDTO createdEvent = eventService.createEvent(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener eventos con filtros y paginación")
    @GetMapping
    public ResponseEntity<Page<EventDTO>> getAllEvents(
            @Parameter(description = "Filtrar por ciudad del recinto") @RequestParam(required = false) String city,
            @Parameter(description = "Filtrar por categoría") @RequestParam(required = false) String category,
            @Parameter(description = "Filtrar por fecha >= a esta") @RequestParam(required = false) LocalDateTime startDate,
             Pageable pageable
    ) {
        return ResponseEntity.ok(eventService.getAllEvents(city, category, startDate, pageable));
    }

    @Operation(summary = "Obtener un evento por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @Operation(summary = "Actualizar un evento")
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @Valid @RequestBody EventDTO eventDetails) {
        return ResponseEntity.ok(eventService.updateEvent(id, eventDetails));
    }

    @Operation(summary = "Eliminar un evento")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}