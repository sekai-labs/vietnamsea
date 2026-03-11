package org.vietnamsea.identity.module.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.vietnamsea.identity.infra.persistence.user.entity.UserEntity;
import org.vietnamsea.identity.module.user.dto.response.BaseUserResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserEntityMapper {
  BaseUserResponse toBaseUserResponse(UserEntity userEntity);
}
