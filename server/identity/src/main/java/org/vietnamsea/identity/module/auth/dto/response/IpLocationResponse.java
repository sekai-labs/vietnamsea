package org.vietnamsea.identity.module.auth.dto.response;

import lombok.Data;

@Data
public class IpLocationResponse {
  private double lat;
  private double lon;
  private String city;
  private String country;
}
