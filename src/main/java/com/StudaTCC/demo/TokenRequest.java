package com.StudaTCC.demo;

public record TokenRequest(String token, Long id) {
    public TokenRequest(TokenRequest tokenJWT) {
        this(tokenJWT.token(), tokenJWT.id());
    }
}
