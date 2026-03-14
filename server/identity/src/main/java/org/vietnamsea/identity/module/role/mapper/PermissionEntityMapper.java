package org.vietnamsea.identity.module.role.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.vietnamsea.identity.infra.persistence.user.entity.PermissionEntity;
import org.vietnamsea.identity.module.role.dto.request.PermissionRequest;
import org.vietnamsea.identity.module.role.dto.response.PermissionResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PermissionEntityMapper {
  PermissionEntity toEntity(PermissionRequest permissionRequest);

  PermissionResponse toDto(PermissionEntity roleEntity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  PermissionEntity partialUpdate(PermissionResponse roleResponse, @MappingTarget PermissionEntity roleEntity);
}
