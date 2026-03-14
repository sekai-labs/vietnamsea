package org.vietnamsea.identity.module.role.service.impl;

import org.springframework.stereotype.Service;
import org.vietnamsea.identity.exception.ActionFailedException;
import org.vietnamsea.identity.exception.ValidationException;
import org.vietnamsea.identity.infra.persistence.user.repository.PermissionRepository;
import org.vietnamsea.identity.module.role.dto.request.PermissionRequest;
import org.vietnamsea.identity.module.role.dto.response.PermissionResponse;
import org.vietnamsea.identity.module.role.mapper.PermissionEntityMapper;
import org.vietnamsea.identity.module.role.service.PermissionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
  private final PermissionRepository permissionRepository;
  private final PermissionEntityMapper mapper;

  @Override
  public void createPermission(PermissionRequest permissionRequest) {
    var permissionEntity = mapper.toEntity(permissionRequest);
    try {
      permissionRepository.save(permissionEntity);
    } catch (Exception ex) {
      throw new ActionFailedException("failed to save permission", ex);
    }
  }

  @Override
  public void updatePermission(Long id, PermissionRequest request) {
    var permissionEntity = permissionRepository.findById(id).orElseThrow(() -> new ValidationException(""));
    if (permissionEntity.getAction() != null && permissionEntity.getAction().equals(request.getAction())) {
      permissionEntity.setAction(request.getAction());
    }
  }

  @Override
  public PermissionResponse findPermissionById(Long id) {
    var permissionEntity = permissionRepository.findById(id).orElseThrow(() -> new ValidationException(""));
    return mapper.toDto(permissionEntity);
  }
}
