package com.tiquetera.infrastructure.adapters.out.persistence.repository;

import com.tiquetera.infrastructure.adapters.out.persistence.entity.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    boolean existsByName(String name);

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