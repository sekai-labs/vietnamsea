package org.vietnamsea.identity.common.helper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

import org.apache.commons.codec.binary.Base32;

import jakarta.servlet.http.HttpServletRequest;

public class TokenUtil {
  private static final SecureRandom secureRandom = new SecureRandom();
  private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

  public static String generateRefreshToken() {
    byte[] randomBytes = new byte[64];
    secureRandom.nextBytes(randomBytes);
    return base64Encoder.encodeToString(randomBytes);
  }

  public static String getTokenFromHeader(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public static String generateOTPValue(String secretKey, String username, String issuer) {
    return "otpauth://totp/"
        + URLEncoder.encode(issuer + ":" + username, StandardCharsets.UTF_8).replace("+", "%20")
        + "?secret=" + URLEncoder.encode(secretKey, StandardCharsets.UTF_8).replace("+", "%20")
        + "&issuer=" + URLEncoder.encode(issuer, StandardCharsets.UTF_8).replace("+", "%20");
  }

  public static String generatePassword(int length) {
    SecureRandom secureRandom = new SecureRandom();
    byte[] bytes = new byte[length];
    secureRandom.nextBytes(bytes);
    Base32 base32 = new Base32();
    return base32.encodeToString(bytes);
  }
}
