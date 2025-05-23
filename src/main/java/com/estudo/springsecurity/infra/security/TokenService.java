package com.estudo.springsecurity.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.estudo.springsecurity.entities.User;

@Service
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secret;

  @Value("${api.security.token.expiration-minutes:120}") // Tempo de expiração (2 horas padrão)
  private long expirationMinutes;

  private Instant generateExpirationDate() {
    return LocalDateTime.now().plusMinutes(expirationMinutes).toInstant(ZoneOffset.of("-03:00"));
  }

  public String generateKeyToken(User user) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);

      String token = JWT.create().withIssuer("com.estudo.springsecurity")
          .withSubject(user.getEmail())
//          .withSubject(user.)
          .withExpiresAt(generateExpirationDate())
          .sign(algorithm);
      return token;

    } catch (JWTCreationException exception) {
      throw new RuntimeException("Erro na geração de Token" + exception);
    }
  }

  public String validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);

      return JWT.require(algorithm)
          .withIssuer("com.estudo.springsecurity")
          .build()
          .verify(token)
          .getSubject();

    } catch (JWTVerificationException exception) {
      // Token inválido será tratado como (403-Forbidden) no SecurityFilter
      return null;
    }
  }
}
