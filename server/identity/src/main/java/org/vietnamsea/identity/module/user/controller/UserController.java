package org.vietnamsea.identity.module.user.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vietnamsea.identity.common.dto.QueryWrapper;
import org.vietnamsea.identity.common.dto.ResponseObject;
import org.vietnamsea.identity.module.user.dto.response.BaseUserResponse;
import org.vietnamsea.identity.module.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping
  ResponseEntity<ResponseObject<List<BaseUserResponse>>> getAllUsers(
      @PageableDefault(page = 0, size = 10) Pageable page, @RequestParam String q) {
    var result = userService.findAllUser(QueryWrapper.builder().wrapSort(page).search(q).build());
    return ResponseEntity.ok(new ResponseObject.Builder<List<BaseUserResponse>>()
        .unwrapPaginationWrapper(result)
        .code("GET_USER_SUCCESS")
        .success(true)
        .messages("Get user success")
        .build());
  }

  @GetMapping("{id}")
  ResponseEntity<ResponseObject<BaseUserResponse>> getUserById(UUID id) {
    var result = userService.findUserById(id);
    return ResponseEntity.ok(new ResponseObject.Builder<BaseUserResponse>()
        .code("GET_USER_SUCCESS")
        .content(result)
        .success(true)
        .messages("Get user success")
        .build());
  }

  @PatchMapping("{id}/disable")
  ResponseEntity<ResponseObject<Void>> disableUser(UUID id) {
    userService.disableUser(id);
    return ResponseEntity.ok(new ResponseObject.Builder<Void>()
        .code("DISABLE_USER_SUCCESS")
        .success(true)
        .messages("Disable user success")
        .build());
  }

  @PatchMapping("{id}/locked")
  ResponseEntity<ResponseObject<Void>> lockedUser(UUID id) {
    userService.lockedUser(id);
    return ResponseEntity.ok(new ResponseObject.Builder<Void>()
        .code("LOCKED_USER_SUCCESS")
        .success(true)
        .messages("Locked user success")
        .build());
  }
}
