package com.tiquetera.infrastructure.adapters.out.persistence.repository;

import com.tiquetera.infrastructure.adapters.out.persistence.entity.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<VenueEntity, Long> {
}