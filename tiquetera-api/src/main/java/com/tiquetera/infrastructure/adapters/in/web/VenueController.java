package com.tiquetera.infrastructure.adapters.in.web;

import com.tiquetera.domain.model.Venue;
import com.tiquetera.domain.ports.in.ManageVenueUseCase;
import com.tiquetera.infrastructure.adapters.in.web.dto.VenueDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // IMPORTANTE
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/venues")
@RequiredArgsConstructor
@Tag(name = "Gestión de Recintos (Hexagonal)")
public class VenueController {

    private final ManageVenueUseCase manageVenueUseCase;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Solo ADMIN puede crear
    public ResponseEntity<VenueDTO> createVenue(@Valid @RequestBody VenueDTO dto) {
        Venue venue = mapToDomain(dto);
        Venue created = manageVenueUseCase.createVenue(venue);
        return new ResponseEntity<>(mapToDto(created), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VenueDTO>> getAllVenues() {
        // Endpoint público para usuarios autenticados
        List<VenueDTO> list = manageVenueUseCase.getAllVenues().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueDTO> getVenueById(@PathVariable Long id) {
        return ResponseEntity.ok(mapToDto(manageVenueUseCase.getVenueById(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Solo ADMIN puede editar
    public ResponseEntity<VenueDTO> updateVenue(@PathVariable Long id, @Valid @RequestBody VenueDTO dto) {
        Venue updated = manageVenueUseCase.updateVenue(id, mapToDomain(dto));
        return ResponseEntity.ok(mapToDto(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Solo ADMIN puede borrar
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        manageVenueUseCase.deleteVenue(id);
        return ResponseEntity.noContent().build();
    }

    // --- Mappers ---
    private Venue mapToDomain(VenueDTO dto) {
        Venue venue = new Venue();
        venue.setId(dto.getId());
        venue.setName(dto.getName());
        venue.setAddress(dto.getAddress());
        venue.setCity(dto.getCity());
        venue.setCapacity(dto.getCapacity());
        return venue;
    }

    private VenueDTO mapToDto(Venue domain) {
        VenueDTO dto = new VenueDTO();
        dto.setId(domain.getId());
        dto.setName(domain.getName());
        dto.setAddress(domain.getAddress());
        dto.setCity(domain.getCity());
        dto.setCapacity(domain.getCapacity());
        return dto;
    }
}