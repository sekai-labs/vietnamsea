package org.vietnamsea.identity.module.role.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.vietnamsea.identity.infra.persistence.user.entity.RoleEntity;
import org.vietnamsea.identity.module.role.dto.request.RoleRequest;
import org.vietnamsea.identity.module.role.dto.response.RoleResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleEntityMapper {
    RoleEntity toEntity(RoleRequest roleResponse);

    RoleResponse toDto(RoleEntity roleEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RoleEntity partialUpdate(RoleResponse roleResponse, @MappingTarget RoleEntity roleEntity);
}
