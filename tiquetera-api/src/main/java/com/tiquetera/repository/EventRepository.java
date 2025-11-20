package com.tiquetera.repository;

import com.tiquetera.entity.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    // Para validar nombres duplicados rápidamente
    boolean existsByName(String name);

    // Query personalizada para filtros dinámicos y paginación
    @Query("SELECT e FROM EventEntity e WHERE " +
            "(:city IS NULL OR e.venue.city = :city) AND " +
            "(:category IS NULL OR e.category = :category) AND " +
            "(:startDate IS NULL OR e.eventDate >= :startDate)")
    Page<EventEntity> findByFilters(
            @Param("city") String city,
            @Param("category") String category,
            @Param("startDate") LocalDateTime startDate,
            Pageable pageable
    );
}