package com.StudaTCC.demo.usuario;

public record ListarUsuarioRequest (
        Long id,
        String nickName
) {
    public ListarUsuarioRequest(Usuario usuario){
        this(usuario.getId(), usuario.getNickName());
    }
}
