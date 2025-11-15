package com.tiquetera.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data // Genera getters, setters, toString, equals, hashCode
@Schema(description = "Representa un recinto o lugar para eventos")
public class VenueDTO {

    @Schema(description = "ID único del recinto", example = "1")
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Schema(description = "Nombre del recinto", example = "Estadio Metropolitano")
    private String name;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Schema(description = "Dirección del recinto", example = "Calle 123, Ciudad")
    private String address;

    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    @Schema(description = "Capacidad máxima de asistentes", example = "45000")
    private int capacity;
}