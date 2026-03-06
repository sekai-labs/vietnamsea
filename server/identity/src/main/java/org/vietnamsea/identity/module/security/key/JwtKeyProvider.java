package org.vietnamsea.identity.module.security.key;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.stereotype.Component;
import org.vietnamsea.identity.config.security.JwtConfig;

import com.nimbusds.jose.jwk.RSAKey;

import lombok.Getter;

@Component
@Getter
public class JwtKeyProvider {
  private final RSAKey rsaKey;

  public JwtKeyProvider(JwtConfig config) throws Exception {
    RSAPrivateKey privateKey = loadPrivateKey(config.getPrivateKeyPath());
    RSAPublicKey publicKey = loadPublicKey(config.getPublicKeyPath());

    this.rsaKey = new RSAKey.Builder(publicKey)
        .privateKey(privateKey)
        .keyID(config.getKeyId())
        .build();
  }

  public RSAKey getPublicJwk() {
    return rsaKey.toPublicJWK();
  }

  private RSAPrivateKey loadPrivateKey(String path) throws Exception {

    String key = Files.readString(Paths.get(path));

    key = key.replace("-----BEGIN PRIVATE KEY-----", "")
        .replace("-----END PRIVATE KEY-----", "")
        .replaceAll("\\s", "");

    byte[] decoded = Base64.getDecoder().decode(key);

    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);

    return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(spec);
  }

  private RSAPublicKey loadPublicKey(String path) throws Exception {

    String key = Files.readString(Paths.get(path));

    key = key.replace("-----BEGIN PUBLIC KEY-----", "")
        .replace("-----END PUBLIC KEY-----", "")
        .replaceAll("\\s", "");

    byte[] decoded = Base64.getDecoder().decode(key);

    X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);

    return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);
  }
}
