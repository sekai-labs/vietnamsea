package org.vietnamsea.identity.module.role.service;

import org.vietnamsea.identity.module.role.dto.request.PermissionRequest;
import org.vietnamsea.identity.module.role.dto.response.PermissionResponse;

public interface PermissionService {

  void createPermission(PermissionRequest permissionRequest);

  void updatePermission(Long id, PermissionRequest request);

  PermissionResponse findPermissionById(Long id);

}
