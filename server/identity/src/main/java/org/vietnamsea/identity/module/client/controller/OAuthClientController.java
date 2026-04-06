package org.vietnamsea.identity.module.client.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vietnamsea.identity.common.dto.ResponseObject;
import org.vietnamsea.identity.module.client.dto.request.OAuthClientRequest;
import org.vietnamsea.identity.module.client.dto.response.OAuthClientResponse;
import org.vietnamsea.identity.module.client.service.OAuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("oauth-client")
@RequiredArgsConstructor
public class OAuthClientController {
  private final OAuthService oAuthService;

  @PostMapping
  ResponseEntity<ResponseObject<Void>> createOAuthClient(@RequestBody @Valid OAuthClientRequest request) {
    oAuthService.createOAuthClient(request);
    return ResponseEntity.ok(new ResponseObject.Builder<Void>()
        .code("SUCCESS")
        .messages("add oauth success")
        .success(true)
        .build());
  }

  @GetMapping("{id}")
  ResponseEntity<ResponseObject<OAuthClientResponse>> getOAuthClientById(@PathVariable Long id) {
    return null;
  }

}
