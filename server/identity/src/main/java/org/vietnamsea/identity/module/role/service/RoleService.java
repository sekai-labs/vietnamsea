package org.vietnamsea.identity.module.role.service;

import java.util.List;

import org.vietnamsea.identity.common.dto.PaginationWrapper;
import org.vietnamsea.identity.common.dto.QueryWrapper;
import org.vietnamsea.identity.module.role.dto.response.RoleResponse;

public interface RoleService {

  RoleResponse findById(Long id);

  PaginationWrapper<List<RoleResponse>> findAllRoles(QueryWrapper queryWrapper);

}
