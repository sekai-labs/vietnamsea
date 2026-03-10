package org.vietnamsea.identity.module.auth.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vietnamsea.identity.module.security.jwks.JwksService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/.well-known")
@RequiredArgsConstructor
public class JwksController {
  private final JwksService jwksService;

  @GetMapping("/jwks.json")
  public ResponseEntity<Map<String, Object>> getKeys() {

    var result = jwksService.getKeys();

    return ResponseEntity.ok(result);
  }
}
