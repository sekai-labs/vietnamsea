package org.vietnamsea.identity.module.role.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.vietnamsea.identity.common.dto.PaginationWrapper;
import org.vietnamsea.identity.common.dto.QueryFieldWrapper;
import org.vietnamsea.identity.common.dto.QueryWrapper;
import org.vietnamsea.identity.exception.ValidationException;
import org.vietnamsea.identity.infra.persistence.user.entity.RoleEntity;
import org.vietnamsea.identity.infra.persistence.user.repository.RoleRepository;
import org.vietnamsea.identity.module.role.dto.response.RoleResponse;
import org.vietnamsea.identity.module.role.mapper.RoleEntityMapper;
import org.vietnamsea.identity.module.role.service.RoleService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;
  private final RoleEntityMapper mapper;

  @Override
  public RoleResponse findById(Long id) {
    var result = roleRepository.findById(id).orElseThrow(() -> new ValidationException("not found role with this id"));
    return mapper.toDto(result);
  }

  @Override
  public PaginationWrapper<List<RoleResponse>> findAllRoles(QueryWrapper queryWrapper) {
    return roleRepository.query(queryWrapper, (param) -> ((root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (query != null) {
        query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
        query.orderBy(criteriaBuilder.desc(root.get("updatedAt")));
      }
      return getPredicate(param, root, criteriaBuilder, predicates);
    }), (items) -> {
      var list = items.map((item) -> mapper.toDto(item)).toList();
      return new PaginationWrapper.Builder<List<RoleResponse>>()
          .setData(list)
          .setPaginationInfo(items)
          .build();
    });
  }

  private Predicate getPredicate(Map<String, QueryFieldWrapper> param, Root<RoleEntity> root,
      CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
    if (param != null && !param.isEmpty()) {
      Predicate[] defaultPredicates = roleRepository.createDefaultPredicate(criteriaBuilder, root, param);
      predicates.addAll(Arrays.asList(defaultPredicates));
    }
    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
