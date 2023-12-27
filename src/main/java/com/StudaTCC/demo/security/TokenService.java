package com.StudaTCC.demo.security;

import com.StudaTCC.demo.TokenRequest;
import com.StudaTCC.demo.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public TokenRequest gerarToken(Usuario usuario){
        try {
            var username = usuario.getUsername();
            var algoritmo = Algorithm.HMAC256(secret);
            var token = JWT.create()
                    .withIssuer("Studa")
                    .withSubject(username)
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
            var id = usuario.getId();
            return new TokenRequest(token, id);
        } catch (JWTCreationException exception){
            throw new RuntimeException("erro ao gerar token jwt", exception);
        }
    }
    public String getSubject(String token){
        try{
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("Studa")
                    .build()
                    .verify(token)
                    .getSubject();

        }catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT inválido ou expirado");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
