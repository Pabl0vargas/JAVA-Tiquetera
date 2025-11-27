package com.tiquetera.infrastructure.adapters.out.persistence;

import com.tiquetera.domain.model.Event;
import com.tiquetera.domain.ports.out.EventRepositoryPort;
import com.tiquetera.infrastructure.adapters.out.persistence.entity.EventEntity;
import com.tiquetera.infrastructure.adapters.out.persistence.mapper.EventPersistenceMapper;
import com.tiquetera.infrastructure.adapters.out.persistence.repository.EventRepository;
import com.tiquetera.infrastructure.adapters.out.persistence.repository.EventSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventPersistenceAdapter implements EventRepositoryPort {

    private final EventRepository eventRepository;
    private final EventPersistenceMapper eventMapper;

    @Override
    public Event save(Event event) {
        return eventMapper.toDomain(eventRepository.save(eventMapper.toEntity(event)));
    }

    @Override
    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id).map(eventMapper::toDomain);
    }

    @Override
    public Page<Event> findByFilters(String city, String category, LocalDateTime startDate, Pageable pageable) {
        // 1. Crear Specification con los filtros
        Specification<EventEntity> spec = EventSpecification.withDynamicFilters(city, category, startDate);

        // 2. Ejecutar consulta optimizada (con EntityGraph implícito en el repo)
        return eventRepository.findAll(spec, pageable)
                .map(eventMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return eventRepository.existsByName(name);
    }

    @Override
    public boolean existsById(Long id) {
        return eventRepository.existsById(id);
    }
}