package com.tiquetera.infrastructure.adapters.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventDTO {
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotNull(message = "La fecha no puede ser nula")
    @Future(message = "La fecha del evento debe ser en el futuro")
    private LocalDateTime eventDate;

    private String category;

    @NotNull(message = "El ID del recinto no puede ser nulo")
    private Long venueId;
}