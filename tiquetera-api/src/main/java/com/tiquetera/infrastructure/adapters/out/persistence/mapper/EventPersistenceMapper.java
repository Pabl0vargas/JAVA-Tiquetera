package com.tiquetera.infrastructure.adapters.out.persistence.mapper;

import com.tiquetera.domain.model.Event;
import com.tiquetera.infrastructure.adapters.out.persistence.entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {VenuePersistenceMapper.class})
public interface EventPersistenceMapper {

    @Mapping(source = "venue", target = "venue")
    EventEntity toEntity(Event event);

    @Mapping(source = "venue", target = "venue")
    Event toDomain(EventEntity entity);
}