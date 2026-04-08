package org.vietnamsea.identity.infra.persistence.client.entity;

import java.io.Serializable;

import org.vietnamsea.identity.constant.AuthProviderEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "oauth_clients")
@Table(name = "oauth_clients")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuthClientEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "name")
  private String name;
  @Enumerated(value = EnumType.STRING)
  @Column(name = "provider")
  private AuthProviderEnum provider;
  @Column(name = "client_id")
  private String clientId;
  @Column(name = "client_secret_hash")
  private String clientSecretHash;
  @Column(name = "authorize_url")
  private String authorizeUrl;
  @Column(name = "redirect_url")
  private String redirectUrl;
  @Column(name = "user_info_url")
  private String userInfoUrl;
  @Column(name = "token_uri")
  private String tokenUri;
  @Column(name = "scope")
  private String scope;
  @Column(name = "encrypted_data_key")
  private String encryptedDataKey;
  @Column(name = "version")
  private Integer version;
  @Column(name = "enabled")
  private Boolean enabled;
}
