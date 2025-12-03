package com.tiquetera.infrastructure.adapters.out.persistence.mapper;

import com.tiquetera.domain.model.User;
import com.tiquetera.infrastructure.adapters.out.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserPersistenceMapper {
    UserEntity toEntity(User user);
    User toDomain(UserEntity entity);
}