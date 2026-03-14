package org.vietnamsea.identity.module.role.dto.request;

import lombok.Data;

@Data
public class PermissionRequest {
  private String code;
  private String resource;
  private String action;
  private String description;
}
