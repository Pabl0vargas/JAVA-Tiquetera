package com.tiquetera.infrastructure.adapters.out.persistence.repository;

import com.tiquetera.infrastructure.adapters.out.persistence.entity.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

// JpaSpecificationExecutor habilita el uso de Specifications dinámicas
public interface EventRepository extends JpaRepository<EventEntity, Long>, JpaSpecificationExecutor<EventEntity> {

    boolean existsByName(String name);


    @Override
    @NonNull
    @EntityGraph(attributePaths = {"venue"})
    Page<EventEntity> findAll(Specification<EventEntity> spec, @NonNull Pageable pageable);
}