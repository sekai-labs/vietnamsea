package org.vietnamsea.identity.module.user.dto.response;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseUserResponse {
  private UUID id;
  private String username;
  private String email;
  private Boolean locked;
  private Boolean disabled;
  private Boolean emailVerified;
  private OffsetDateTime createdAt;
  private Timestamp lastLoginAt;
}
