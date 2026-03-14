package org.vietnamsea.identity.module.user.service;

import java.util.List;
import java.util.UUID;

import org.vietnamsea.identity.common.dto.PaginationWrapper;
import org.vietnamsea.identity.common.dto.QueryWrapper;
import org.vietnamsea.identity.module.user.dto.response.BaseUserResponse;

public interface UserService {
  BaseUserResponse findUserById(UUID id);

  PaginationWrapper<List<BaseUserResponse>> findAllUser(QueryWrapper queryWrapper);

  void disableUser(UUID id);

  void lockedUser(UUID id);
}
