package org.vietnamsea.identity.module.security.jwt.impl;

import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.vietnamsea.identity.config.security.JwtConfig;
import org.vietnamsea.identity.module.security.jwt.JwtService;
import org.vietnamsea.identity.module.security.key.JwtKeyProvider;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
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
}
