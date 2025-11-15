package com.tiquetera.controller;

import com.tiquetera.dto.VenueDTO;
import com.tiquetera.service.VenueService;
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
@RequestMapping("/venues")
@Tag(name = "Gestión de Recintos (Venues)", description = "API para el CRUD de recintos")
public class VenueController {

    @Autowired
    private VenueService venueService;

    @Operation(summary = "Crear un nuevo recinto",
            description = "Permite registrar un nuevo recinto en memoria.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recinto creado exitosamente",
                            content = @Content(schema = @Schema(implementation = VenueDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
            })
    @PostMapping
    public ResponseEntity<VenueDTO> createVenue(@Valid @RequestBody VenueDTO venue) {
        VenueDTO createdVenue = venueService.createVenue(venue);
        return new ResponseEntity<>(createdVenue, HttpStatus.CREATED); // 201 Created
    }

    @Operation(summary = "Obtener todos los recintos",
            description = "Devuelve una lista de todos los recintos almacenados.")
    @GetMapping
    public ResponseEntity<List<VenueDTO>> getAllVenues() {
        return ResponseEntity.ok(venueService.getAllVenues()); // 200 OK
    }

    @Operation(summary = "Obtener un recinto por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recinto encontrado"),
                    @ApiResponse(responseCode = "404", description = "Recinto no encontrado")
            })
    @GetMapping("/{id}")
    public ResponseEntity<VenueDTO> getVenueById(@PathVariable Long id) {
        return ResponseEntity.ok(venueService.getVenueById(id));
    }

    @Operation(summary = "Actualizar un recinto por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recinto actualizado"),
                    @ApiResponse(responseCode = "404", description = "Recinto no encontrado"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            })
    @PutMapping("/{id}")
    public ResponseEntity<VenueDTO> updateVenue(@PathVariable Long id, @Valid @RequestBody VenueDTO venueDetails) {
        return ResponseEntity.ok(venueService.updateVenue(id, venueDetails));
    }

    @Operation(summary = "Eliminar un recinto por ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Recinto eliminado"),
                    @ApiResponse(responseCode = "404", description = "Recinto no encontrado")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}