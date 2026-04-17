package org.vietnamsea.identity.module.auth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.ValidationFailureException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vietnamsea.identity.common.constant.AuthProviderEnum;
import org.vietnamsea.identity.exception.ActionFailedException;
import org.vietnamsea.identity.infra.persistence.credential.repository.OAuthCredentialRepository;
import org.vietnamsea.identity.module.auth.config.OAuth2ProviderConfig;
import org.vietnamsea.identity.module.auth.dto.request.AuthRequest;
import org.vietnamsea.identity.module.auth.dto.response.AuthIdentityResponse;
import org.vietnamsea.identity.module.auth.service.AuthProvider;
import org.vietnamsea.identity.module.client.service.OAuthProviderRegistry;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GithubAuthProviderServiceImpl implements AuthProvider {
  private final OAuthProviderRegistry oathProviderRegistry;
  private final OAuthCredentialRepository oAuthCredentialRepository;

  @Override
  public AuthProviderEnum getProvider() {
    return AuthProviderEnum.GITHUB;
  }

  private OAuth2ProviderConfig getConfig() {
    return oathProviderRegistry.getConfig(getProvider());
  }

  @Override
  public AuthIdentityResponse authenticate(AuthRequest request) {
    try {
      var config = getConfig();
      var accessToken = getAccessToken(request, config);
      var profile = getGithubUser(accessToken, config);
      var email = getPrimaryEmail(accessToken, config);

      String githubId = String.valueOf(profile.get("id"));
      var credential = oAuthCredentialRepository.findByProvider_ProviderAndProviderUserId(getProvider(),
          githubId)
          .orElseThrow(() -> new ValidationFailureException("this provider is currently not supported"));
      return AuthIdentityResponse.builder()
          .identity(credential.getUser().getId())
          .providerId(githubId)
          .email(email)
          .name(githubId)
          .build();
    } catch (Exception ex) {
      throw new ActionFailedException("failed to authenticate with this method", ex);
    }
  }

  public Map<String, Object> getGithubUser(String accessToken, OAuth2ProviderConfig config) {
    RestTemplate rest = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));

    HttpEntity<Void> request = new HttpEntity<>(headers);

    ResponseEntity<Map<String, Object>> response = rest.exchange(
        config.getUserInfoUrl(),
        HttpMethod.GET,
        request,
        new ParameterizedTypeReference<Map<String, Object>>() {
        });

    return response.getBody();
  }

  public String getPrimaryEmail(String accessToken, OAuth2ProviderConfig config) {
    RestTemplate rest = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));

    HttpEntity<Void> request = new HttpEntity<>(headers);

    ResponseEntity<List<Map<String, Object>>> response = rest.exchange(
        config.getUserInfoUrl() + "/emails",
        HttpMethod.GET,
        request,
        new ParameterizedTypeReference<List<Map<String, Object>>>() {
        });

    var emails = response.getBody();
    return emails.stream()
        .filter(e -> Boolean.TRUE.equals(e.get("primary")))
        .map(e -> (String) e.get("email"))
        .findFirst()
        .orElse(null);
  }

  private String getAccessToken(AuthRequest authRequest, OAuth2ProviderConfig config) {
    var rest = new RestTemplate();
    var headers = new HttpHeaders();
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);
    Map<String, String> body = new HashMap<>();
    body.put("client_id", config.getClientId());
    body.put("client_secret", config.getClientSecret());
    body.put("code", authRequest.getCode());
    HttpEntity<Map<String, String>> restRequest = new HttpEntity<>(body, headers);

    ResponseEntity<Map<String, Object>> response = rest.exchange(
        config.getAuthorizeUrl(),
        HttpMethod.GET,
        restRequest,
        new ParameterizedTypeReference<Map<String, Object>>() {
        });

    if (response.getStatusCode() != HttpStatusCode.valueOf(200)) {
      throw new ActionFailedException("Failed to get access token");
    }
    Map<String, Object> responseBody = response.getBody();
    String accessToken = responseBody != null
        ? (responseBody.get("access_token") != null ? responseBody.get("access_token").toString() : "")
        : "";

    if (accessToken.isEmpty()) {
      throw new RuntimeException("Failed to get access token: " + responseBody);
    }
    return "";
  }

}
