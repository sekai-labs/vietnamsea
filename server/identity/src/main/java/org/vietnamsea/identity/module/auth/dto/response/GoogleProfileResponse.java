package org.vietnamsea.identity.module.auth.dto.response;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class GoogleProfileResponse implements Serializable {
  private String sub;
  private String name;
  @SerializedName("given_name")
  private String givenName;
  @SerializedName("family_name")
  private String familyName;
  private String picture;
  private String email;
  @SerializedName("email_verified")
  private boolean emailVerified;
}
