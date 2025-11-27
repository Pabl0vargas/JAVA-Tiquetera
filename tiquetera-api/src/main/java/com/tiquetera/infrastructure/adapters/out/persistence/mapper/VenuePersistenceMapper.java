package com.tiquetera.infrastructure.adapters.out.persistence.mapper;

import com.tiquetera.domain.model.Venue;
import com.tiquetera.infrastructure.adapters.out.persistence.entity.VenueEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VenuePersistenceMapper {
    VenueEntity toEntity(Venue venue);
    Venue toDomain(VenueEntity entity);
}