package com.tiquetera.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Representa un evento en la tiquetera")
public class EventDTO {

    @Schema(description = "ID único del evento", example = "101")
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Schema(description = "Nombre del evento", example = "Concierto de Rock")
    private String name;

    @NotNull(message = "La fecha no puede ser nula")
    @Schema(description = "Fecha y hora del evento", example = "2025-12-25T20:00:00")
    private LocalDateTime eventDate;

    @NotNull(message = "El ID del recinto no puede ser nulo")
    @Schema(description = "ID del recinto (Venue) donde ocurre el evento", example = "1")
    private Long venueId; // Relación simple con Venue
}