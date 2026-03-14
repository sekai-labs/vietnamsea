package org.vietnamsea.identity.module.role.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vietnamsea.identity.common.dto.ResponseObject;
import org.vietnamsea.identity.module.role.dto.request.PermissionRequest;
import org.vietnamsea.identity.module.role.dto.response.PermissionResponse;
import org.vietnamsea.identity.module.role.service.PermissionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("permissions")
public class PermissionController {
  private final PermissionService permissionService;

  @GetMapping("{id}")
  ResponseEntity<ResponseObject<PermissionResponse>> findPermissionById(Long id) {
    var result = permissionService.findPermissionById(id);
    return ResponseEntity.ok(new ResponseObject.Builder<PermissionResponse>()
        .code("GET_SUCCESS")
        .success(true)
        .messages("Get success")
        .content(result)
        .build());
  }

  @PostMapping
  ResponseEntity<ResponseObject<Void>> createPermission(@RequestBody PermissionRequest request) {
    permissionService.createPermission(request);
    return ResponseEntity.ok(new ResponseObject.Builder<Void>()
        .code("CREATE_SUCCESS")
        .success(true)
        .messages("Create success")
        .build());
  }

  @PutMapping("{id}")
  ResponseEntity<ResponseObject<Void>> updatePermission(Long id, @RequestBody PermissionRequest request) {
    permissionService.updatePermission(id, request);
    return ResponseEntity.ok(new ResponseObject.Builder<Void>()
        .code("UPDATE_SUCCESS")
        .success(true)
        .messages("Update success")
        .build());
  }
}
