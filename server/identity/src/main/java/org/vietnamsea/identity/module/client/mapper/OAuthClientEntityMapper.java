package org.vietnamsea.identity.module.client.mapper;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.vietnamsea.identity.infra.persistence.client.entity.OAuthClientEntity;
import org.vietnamsea.identity.module.client.dto.request.OAuthClientRequest;
import org.vietnamsea.identity.module.client.dto.response.OAuthClientResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OAuthClientEntityMapper {
  OAuthClientEntity toEntity(OAuthClientRequest request);

  OAuthClientResponse toDto(OAuthClientEntity clientEntity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  OAuthClientEntity particleUpdate(OAuthClientRequest request, @MappingTarget OAuthClientEntity oAuthClientEntity);

  default Set<String> map(String value) {
    if (value == null || value.isBlank()) {
      return Set.of();
    }
    return Arrays.stream(value.split(","))
        .map(String::trim)
        .filter(item -> !item.isEmpty())
        .collect(Collectors.toSet());
  }

  default String map(Set<String> value) {
    if (value == null || value.isEmpty()) {
      return "";
    }
    return value.stream().map(String::trim).filter(item -> !item.isEmpty()).collect(Collectors.joining(","));
  }
}
