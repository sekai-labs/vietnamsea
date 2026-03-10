package org.vietnamsea.identity.infra.persistence.client.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class OAuthClientEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "name")
  private String name;
  @Column(name = "client_id", nullable = false)
  private String clientId;
  @Column(name = "client_secret_hash", nullable = false)
  private String clientSecretHash;
  @Column(name = "redirect_urls", nullable = false)
  private String redirectUrls;
  @Column(name = "grant_types")
  private String grantTypes;
  @Column(name = "scope")
  private String scope;
}
