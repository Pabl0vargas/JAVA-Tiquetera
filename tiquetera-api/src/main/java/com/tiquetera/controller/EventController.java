package com.tiquetera.controller;

import com.tiquetera.dto.EventDTO;
import com.tiquetera.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@Tag(name = "Gestión de Eventos", description = "API para el CRUD de eventos")
public class EventController {

    @Autowired
    private EventService eventService;

    @Operation(summary = "Crear un nuevo evento",
            description = "Permite registrar un nuevo evento en memoria.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Evento creado exitosamente",
                            content = @Content(schema = @Schema(implementation = EventDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
            })
    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO event) {
        EventDTO createdEvent = eventService.createEvent(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED); // 201 Created
    }

    @Operation(summary = "Obtener todos los eventos",
            description = "Devuelve una lista de todos los eventos almacenados.")
    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents()); // 200 OK
    }

    @Operation(summary = "Obtener un evento por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evento encontrado"),
                    @ApiResponse(responseCode = "404", description = "Evento no encontrado")
            })
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @Operation(summary = "Actualizar un evento por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evento actualizado"),
                    @ApiResponse(responseCode = "404", description = "Evento no encontrado"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            })
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @Valid @RequestBody EventDTO eventDetails) {
        return ResponseEntity.ok(eventService.updateEvent(id, eventDetails));
    }

    @Operation(summary = "Eliminar un evento por ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Evento eliminado"),
                    @ApiResponse(responseCode = "404", description = "Evento no encontrado")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}