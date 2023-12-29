package com.StudaTCC.demo.usuario;

public record EditUsuarioRequest(
    String nome,
    String sobrenome,
    String nickName,
    String email,
    String senha,
    String imagemPerfil) {
}
