package com.tiquetera.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Representa un recinto o lugar para eventos")
public class VenueDTO {

    @Schema(description = "ID único del recinto", example = "1")
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Schema(example = "Estadio Metropolitano")
    private String name;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Schema(example = "Calle 123, Ciudad")
    private String address;

    @NotBlank(message = "La ciudad es obligatoria")
    @Schema(description = "Ciudad donde se ubica", example = "Medellin")
    private String city;

    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    @Schema(example = "45000")
    private int capacity;
}