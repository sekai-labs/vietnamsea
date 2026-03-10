package org.vietnamsea.identity.module.security.jwt.impl;

import java.time.Instant;
import java.util.Date;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.stereotype.Service;
import org.vietnamsea.identity.config.security.JwtConfig;
import org.vietnamsea.identity.exception.AuthException;
import org.vietnamsea.identity.module.security.jwt.JwtService;
import org.vietnamsea.identity.module.security.key.JwtKeyProvider;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
        private final JwtKeyProvider keyProvider;
        private final JwtConfig jwtConfig;

        @Override
        public String generateToken(String userId) throws Exception {
                JWSSigner signer = new RSASSASigner(
                                keyProvider.getRsaKey().toRSAPrivateKey());

                JWTClaimsSet claims = new JWTClaimsSet.Builder()
                                .subject(userId)
                                .issuer(jwtConfig.getIssuer())
                                .issueTime(Date.from(Instant.now()))
                                .expirationTime(Date.from(Instant.now().plusSeconds(jwtConfig.getMaxAge())))
                                .build();

                SignedJWT jwt = new SignedJWT(
                                new JWSHeader.Builder(JWSAlgorithm.RS256)
                                                .keyID(keyProvider.getRsaKey().getKeyID())
                                                .build(),
                                claims);

                jwt.sign(signer);

                return jwt.serialize();
        }

        @Override
        public JwtClaimsSet verify(String token) {
                try {
                        SignedJWT jwt = SignedJWT.parse(token);

                        JWSVerifier verifier = new RSASSAVerifier(keyProvider.getPublicJwk().toRSAPublicKey());

                        if (!jwt.verify(verifier)) {
                                throw new AuthException("Invalid JWT signature");
                        }

                        JWTClaimsSet claims = jwt.getJWTClaimsSet();

                        if (jwtConfig.getIssuer() != null && !jwtConfig.getIssuer().equals(claims.getIssuer())) {
                                throw new RuntimeException("Invalid token issuer");
                        }

                        if (claims.getExpirationTime() != null
                                        && claims.getExpirationTime().before(Date.from(Instant.now()))) {
                                throw new RuntimeException("Token expired");
                        }

                        var builder = JwtClaimsSet.builder();

                        if (claims.getIssuer() != null)
                                builder.issuer(claims.getIssuer());
                        if (claims.getSubject() != null)
                                builder.subject(claims.getSubject());
                        if (claims.getIssueTime() != null)
                                builder.issuedAt(claims.getIssueTime().toInstant());
                        if (claims.getExpirationTime() != null)
                                builder.expiresAt(claims.getExpirationTime().toInstant());

                        claims.getClaims().forEach((k, v) -> {
                                try {
                                        builder.claim(k, v);
                                } catch (Exception ignored) {
                                }
                        });

                        return builder.build();
                } catch (RuntimeException re) {
                        throw re;
                } catch (Exception e) {
                        throw new RuntimeException("Failed to verify JWT", e);
                }
        }

}
