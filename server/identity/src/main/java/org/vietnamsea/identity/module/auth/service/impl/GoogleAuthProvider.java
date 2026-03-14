package org.vietnamsea.identity.module.auth.service.impl;

import java.io.IOException;

import org.springframework.oxm.ValidationFailureException;
import org.springframework.stereotype.Service;
import org.vietnamsea.identity.config.provider.GoogleConfig;
import org.vietnamsea.identity.constant.AuthProviderEnum;
import org.vietnamsea.identity.exception.ActionFailedException;
import org.vietnamsea.identity.infra.persistence.credential.repository.OAuthCredentialRepository;
import org.vietnamsea.identity.module.auth.dto.request.AuthRequest;
import org.vietnamsea.identity.module.auth.dto.response.AuthIdentityResponse;
import org.vietnamsea.identity.module.auth.dto.response.GoogleProfileResponse;
import org.vietnamsea.identity.module.auth.service.AuthProvider;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoogleAuthProvider implements AuthProvider {
  private final GoogleConfig config;
  private final OAuthCredentialRepository oAuthCredentialRepository;
  private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
  private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  private static final Gson GSON = new Gson();

  @Override
  public AuthProviderEnum getProvider() {
    return AuthProviderEnum.GOOGLE;
  }

  @Override
  public AuthIdentityResponse authenticate(AuthRequest request) {
    try {
      GoogleTokenResponse response = new GoogleAuthorizationCodeTokenRequest(
          HTTP_TRANSPORT,
          JSON_FACTORY,
          config.getClientId(), config.getClientSecret(),
          request.getCode(), config.getRedirectUrl())
          .execute();

      var googleProfile = fetchGoogleProfile(response.getAccessToken());
      var credential = oAuthCredentialRepository.findByProvider_NameAndProviderUserId("Google", googleProfile.getSub())
          .orElseThrow(() -> new ValidationFailureException("this provider is currently not supported"));
      return AuthIdentityResponse.builder()
          .identity(credential.getUser().getId())
          .email(googleProfile.getEmail())
          .name(googleProfile.getName())
          .providerId(getProvider().toString())
          .build();
    } catch (Exception ex) {
      throw new ActionFailedException("there is error when auth with google", ex);
    }
  }

  private GoogleProfileResponse fetchGoogleProfile(String accessToken) {

    try {

      HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory();

      HttpRequest request = requestFactory.buildGetRequest(
          new GenericUrl(config.getUserProfileEndpoint()));

      request.getHeaders().setAuthorization("Bearer " + accessToken);

      HttpResponse response = request.execute();

      if (response.getStatusCode() != HttpStatusCodes.STATUS_CODE_OK) {
        throw new ActionFailedException("there is error when auth with google");
      }

      String json = response.parseAsString();

      return GSON.fromJson(json, GoogleProfileResponse.class);

    } catch (IOException ex) {
      throw new ActionFailedException("there is error when auth with google", ex);
    }
  }
}
