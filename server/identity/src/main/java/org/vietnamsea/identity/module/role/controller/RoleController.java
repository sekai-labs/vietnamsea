package org.vietnamsea.identity.module.role.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vietnamsea.identity.common.dto.QueryWrapper;
import org.vietnamsea.identity.common.dto.ResponseObject;
import org.vietnamsea.identity.module.role.dto.request.RoleRequest;
import org.vietnamsea.identity.module.role.dto.response.RoleResponse;
import org.vietnamsea.identity.module.role.service.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("roles")
public class RoleController {
  private final RoleService roleService;

  @GetMapping
  ResponseEntity<ResponseObject<List<RoleResponse>>> findAllRoles(Pageable page, String q) {
    var result = roleService.findAllRoles(QueryWrapper.builder()
        .wrapSort(page)
        .search(q)
        .build());
    return ResponseEntity.ok(new ResponseObject.Builder<List<RoleResponse>>()
        .code("GET_SUCCESS")
        .success(true)
        .messages("Get success")
        .unwrapPaginationWrapper(result)
        .build());
  }

  @GetMapping("{id}")
  ResponseEntity<ResponseObject<RoleResponse>> findById(Long id) {
    var result = roleService.findById(id);
    return ResponseEntity.ok(new ResponseObject.Builder<RoleResponse>()
        .code("GET_SUCCESS")
        .success(true)
        .content(result)
        .messages("Get success")
        .build());
  }

  @PostMapping
  ResponseEntity<ResponseObject<Void>> createRole(@RequestBody RoleRequest request) {
    roleService.createRole(request);
    return ResponseEntity.ok(new ResponseObject.Builder<Void>()
        .code("CREATE_SUCCESS")
        .success(true)
        .messages("Create success")
        .build());
  }

  @PutMapping("{id}")
  ResponseEntity<ResponseObject<Void>> updateRole(Long id, @RequestBody RoleRequest request) {
    roleService.updateRole(id, request);
    return ResponseEntity.ok(new ResponseObject.Builder<Void>()
        .code("UPDATE_SUCCESS")
        .success(true)
        .messages("Update success")
        .build());
  }
}
