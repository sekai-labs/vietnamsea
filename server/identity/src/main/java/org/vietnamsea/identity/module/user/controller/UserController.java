package org.vietnamsea.identity.module.user.controller;

import org.springframework.web.bind.annotation.RestController;
import org.vietnamsea.identity.module.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
}
