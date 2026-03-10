package org.vietnamsea.identity.common.helper;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {
  public String getUsernameFromAuthentication() {
    try {
      var auth = SecurityContextHolder.getContext().getAuthentication();
      if (auth == null) {
        throw new AuthenticationException("Authentication required") {
        };
      }
      return auth.getName();
    } catch (Exception ex) {
      throw new AuthenticationException("This user isn't authentication, please login again") {
      };
    }
  }
}
