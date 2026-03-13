package org.vietnamsea.identity.module.user.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.vietnamsea.identity.common.dto.PaginationWrapper;
import org.vietnamsea.identity.common.dto.QueryFieldWrapper;
import org.vietnamsea.identity.common.dto.QueryWrapper;
import org.vietnamsea.identity.exception.ActionFailedException;
import org.vietnamsea.identity.exception.ValidationException;
import org.vietnamsea.identity.infra.persistence.user.entity.UserEntity;
import org.vietnamsea.identity.infra.persistence.user.repository.UserRepository;
import org.vietnamsea.identity.module.user.dto.response.BaseUserResponse;
import org.vietnamsea.identity.module.user.mapper.UserEntityMapper;
import org.vietnamsea.identity.module.user.service.UserService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserEntityMapper mapper;

  @Override
  public BaseUserResponse findUserById(UUID id) {
    var userEntity = userRepository.findById(id)
        .orElseThrow(() -> new ValidationException("not found user with this id"));

    return mapper.toBaseUserResponse(userEntity);
  }

  @Override
  public PaginationWrapper<List<BaseUserResponse>> findAllUser(QueryWrapper queryWrapper) {
    return userRepository.query(queryWrapper, (param) -> ((root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (query != null) {
        query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
        query.orderBy(criteriaBuilder.desc(root.get("updatedAt")));
      }
      return getPredicate(param, root, criteriaBuilder, predicates);
    }), (items) -> {
      var list = items.map((item) -> mapper.toBaseUserResponse(item)).toList();
      return new PaginationWrapper.Builder<List<BaseUserResponse>>()
          .setData(list)
          .setPaginationInfo(items)
          .build();
    });
  }

  @Override
  public void disableUser(UUID id) {
    var userEntity = userRepository.findById(id)
        .orElseThrow(() -> new ValidationException("not found user with this id"));
    if (userEntity.getDisabled()) {
      throw new ValidationException("this user is already disabled");
    }
    userEntity.setDisabled(true);
    try {
      userRepository.saveAndFlush(userEntity);
    } catch (Exception ex) {
      throw new ActionFailedException("fail to disable user, please contact with admin for more detail");
    }
  }

  @Override
  public void lockedUser(UUID id) {
    var userEntity = userRepository.findById(id)
        .orElseThrow(() -> new ValidationException("not found user with this id"));
    if (userEntity.getDisabled()) {
      throw new ValidationException("this user is already disabled");
    }
    userEntity.setDisabled(true);
    try {
      userRepository.saveAndFlush(userEntity);
    } catch (Exception ex) {
      throw new ActionFailedException("fail to disable user, please contact with admin for more detail");
    }
  }

  private Predicate getPredicate(Map<String, QueryFieldWrapper> param, Root<UserEntity> root,
      CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
    if (param != null && !param.isEmpty()) {
      Predicate[] defaultPredicates = userRepository.createDefaultPredicate(criteriaBuilder, root, param);
      predicates.addAll(Arrays.asList(defaultPredicates));
    }
    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }

}
