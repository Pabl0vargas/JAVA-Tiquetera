package com.tiquetera.service;

import com.tiquetera.dto.VenueDTO;
import com.tiquetera.entity.VenueEntity;
import com.tiquetera.exception.ResourceNotFoundException;
import com.tiquetera.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VenueService {

    @Autowired
    private VenueRepository venueRepository;

    public List<VenueDTO> getAllVenues() {
        return venueRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public VenueDTO getVenueById(Long id) {
        VenueEntity entity = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue no encontrado con id: " + id));
        return convertToDTO(entity);
    }

    public VenueDTO createVenue(VenueDTO venueDTO) {
        VenueEntity entity = convertToEntity(venueDTO);
        VenueEntity savedEntity = venueRepository.save(entity);
        return convertToDTO(savedEntity);
    }

    public VenueDTO updateVenue(Long id, VenueDTO venueDetails) {
        VenueEntity entity = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue no encontrado con id: " + id));

        entity.setName(venueDetails.getName());
        entity.setAddress(venueDetails.getAddress());
        entity.setCity(venueDetails.getCity());
        entity.setCapacity(venueDetails.getCapacity());

        return convertToDTO(venueRepository.save(entity));
    }

    public void deleteVenue(Long id) {
        if (!venueRepository.existsById(id)) {
            throw new ResourceNotFoundException("Venue no encontrado con id: " + id);
        }
        venueRepository.deleteById(id);
    }

    // Mappers manuales
    private VenueDTO convertToDTO(VenueEntity entity) {
        VenueDTO dto = new VenueDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setCity(entity.getCity());
        dto.setCapacity(entity.getCapacity());
        return dto;
    }

    private VenueEntity convertToEntity(VenueDTO dto) {
        VenueEntity entity = new VenueEntity();
        // El ID no se setea al crear, lo genera la DB
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setCity(dto.getCity());
        entity.setCapacity(dto.getCapacity());
        return entity;
    }
}