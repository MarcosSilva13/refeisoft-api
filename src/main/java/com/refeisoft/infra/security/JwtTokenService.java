package com.refeisoft.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.refeisoft.domain.entity.User;
import com.refeisoft.infra.exception.TokenInvalidException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class JwtTokenService {

    @Value("${jwt.secret}")
    private String secretKey;
    private static final String ISSUER = "Refeisoft";

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getUsername())
                    .withIssuedAt(generateIssuedTime())
                    .withExpiresAt(generateExpirationTime())
                    .sign(algorithm);
        } catch (IllegalArgumentException | JWTCreationException ex) {
            throw new RuntimeException("Falha ao gerar o token de autenticação.");
        }
    }

    public String getTokenSubject(String tokenJwt) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(tokenJwt)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            throw new TokenInvalidException("Token inválido ou expirado.");
        }
    }

    private Instant generateIssuedTime() {
        return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();
    }

    private Instant generateExpirationTime() {
        return LocalDateTime.now().plusHours(8).atZone(ZoneId.systemDefault()).toInstant();
    }
}
