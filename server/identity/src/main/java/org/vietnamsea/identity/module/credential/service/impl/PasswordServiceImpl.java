package org.vietnamsea.identity.module.credential.service.impl;

import java.time.Duration;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.vietnamsea.identity.exception.ValidationException;
import org.vietnamsea.identity.infra.persistence.user.repository.UserRepository;
import org.vietnamsea.identity.module.credential.dto.request.ForgotPasswordRequest;
import org.vietnamsea.identity.module.credential.service.PasswordService;
import org.vietnamsea.identity.module.database.service.RedisService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {
  private final UserRepository userRepository;
  private final RedisService redisService;

  public void initForgotPasswordSubmit(ForgotPasswordRequest request) {
    var user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new ValidationException("not found user with this id"));
    if (user.getDisabled()) {
      throw new ValidationException("this user is disabled, please contact admin for reactivate");
    }
    var token = UUID.randomUUID().toString();
    redisService.setString(token, user.getId().toString(), Duration.ofMinutes(15));
  }
}
