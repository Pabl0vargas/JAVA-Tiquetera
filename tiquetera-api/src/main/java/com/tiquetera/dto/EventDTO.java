package com.tiquetera.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
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
    @Schema(example = "Concierto de Rock")
    private String name;

    @NotNull(message = "La fecha no puede ser nula")
    @Future(message = "La fecha del evento debe ser en el futuro")
    @Schema(example = "2025-12-25T20:00:00")
    private LocalDateTime eventDate;

    @Schema(description = "Categoría del evento", example = "Música")
    private String category;

    @NotNull(message = "El ID del recinto no puede ser nulo")
    @Schema(description = "ID del recinto donde ocurre el evento", example = "1")
    private Long venueId;
}