package com.tiquetera.infrastructure.adapters.out.persistence;

import com.tiquetera.domain.model.Venue;
import com.tiquetera.domain.ports.out.VenueRepositoryPort;
import com.tiquetera.infrastructure.adapters.out.persistence.mapper.VenuePersistenceMapper;
import com.tiquetera.infrastructure.adapters.out.persistence.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VenuePersistenceAdapter implements VenueRepositoryPort {

    private final VenueRepository venueRepository;
    private final VenuePersistenceMapper venueMapper;

    @Override
    public Venue save(Venue venue) {
        return venueMapper.toDomain(venueRepository.save(venueMapper.toEntity(venue)));
    }

    @Override
    public Optional<Venue> findById(Long id) {
        return venueRepository.findById(id).map(venueMapper::toDomain);
    }

    @Override
    public List<Venue> findAll() {
        return venueRepository.findAll().stream().map(venueMapper::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        venueRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return venueRepository.existsById(id);
    }
}