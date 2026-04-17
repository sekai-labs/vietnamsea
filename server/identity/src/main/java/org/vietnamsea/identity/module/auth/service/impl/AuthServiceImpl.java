package org.vietnamsea.identity.module.auth.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.vietnamsea.identity.common.constant.AuthProviderEnum;
import org.vietnamsea.identity.common.helper.TokenUtil;
import org.vietnamsea.identity.exception.ActionFailedException;
import org.vietnamsea.identity.exception.ValidationException;
import org.vietnamsea.identity.infra.persistence.session.entity.LoginHistoryEntity;
import org.vietnamsea.identity.infra.persistence.session.repository.LoginHistoryRepository;
import org.vietnamsea.identity.infra.persistence.user.repository.UserRepository;
import org.vietnamsea.identity.module.auth.dto.request.AuthRequest;
import org.vietnamsea.identity.module.auth.dto.response.AuthResponse;
import org.vietnamsea.identity.module.auth.message.AuthNotificationProducer;
import org.vietnamsea.identity.module.auth.service.AuthAnomalyDetector;
import org.vietnamsea.identity.module.auth.service.AuthProvider;
import org.vietnamsea.identity.module.auth.service.AuthService;
import org.vietnamsea.identity.module.security.jwt.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final JwtService jwtService;
  private final Map<AuthProviderEnum, AuthProvider> providers;
  private final AuthNotificationProducer producer;
  private final AuthAnomalyDetector authAnomalyDetector;
  private final LoginHistoryRepository loginHistoryRepository;
  private final UserRepository userRepository;

  public AuthResponse authentication(AuthRequest request, HttpServletRequest servletRequest) {
    var provider = providers.get(request.getProvider());
    if (provider == null) {
      throw new ValidationException("This provider is not supported");
    }

    if (request.getProvider() != AuthProviderEnum.LOCAL && (request.getCode() == null || request.getCode().isBlank())) {
      throw new ValidationException("authorization code is required for oauth provider");
    }

    var identity = provider.authenticate(request);
    var loginHistory = loginHistoryRepository.findTop1ByUser_IdOrderByCreatedAtDesc(identity.getIdentity())
        .orElse(null);

    try {
      var accessToken = jwtService.generateToken(identity.getIdentity().toString());
      var refreshToken = TokenUtil.generateRefreshToken();

      var ip = authAnomalyDetector.getClientIp(servletRequest);
      var location = authAnomalyDetector.getLocationFromIP(ip);

      var isSuspicious = location != null && authAnomalyDetector.isSuspicious(loginHistory, location);
      if (isSuspicious) {
        var coordinates = new HashMap<String, Double>();
        if (location == null) {
          coordinates = null;
        } else {
          coordinates.put("latitude", location.getLat());
          coordinates.put("longitude", location.getLon());
        }
        producer.sendLoginWarning(identity.getName(), identity.getEmail(), coordinates);
      }

      var user = userRepository.getReferenceById(identity.getIdentity());
      var loginHistorySave = LoginHistoryEntity.builder()
          .user(user)
          .ipAddress(ip)
          .userAgent(servletRequest.getHeader("User-Agent"))
          .success(true)
          .city(location != null ? location.getCity() : null)
          .country(location != null ? location.getCountry() : null)
          .lat(location != null ? location.getLat() : null)
          .lon(location != null ? location.getLon() : null)
          .build();

      loginHistoryRepository.save(loginHistorySave);

      return AuthResponse.builder()
          .accessToken(accessToken)
          .refreshToken(refreshToken)
          .build();
    } catch (Exception ex) {
      throw new ActionFailedException("failed to auth with this provider", ex);
    }
  }
}
