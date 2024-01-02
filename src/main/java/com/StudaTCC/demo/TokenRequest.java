package com.StudaTCC.demo;

public record TokenRequest(
        String token
) {
    public TokenRequest(TokenRequest tokenJWT) {
        this(tokenJWT.token());
    }
}
