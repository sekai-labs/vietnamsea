package org.vietnamsea.identity.module.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vietnamsea.identity.common.dto.ResponseObject;
import org.vietnamsea.identity.module.auth.dto.request.LocalAuthRequest;
import org.vietnamsea.identity.module.auth.dto.response.AuthResponse;
import org.vietnamsea.identity.module.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
  private final AuthService authService;

  @PostMapping("local")
  ResponseEntity<ResponseObject<AuthResponse>> localAuthentication(@RequestBody LocalAuthRequest request) {
    var result = authService.localAuthentication(request);
    return ResponseEntity.ok(new ResponseObject.Builder<AuthResponse>()
        .success(true)
        .code("SUCCESS")
        .content(result)
        .messages("Login success")
        .build());
  }
}
