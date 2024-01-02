package com.StudaTCC.demo.usuario.DTO;

import com.StudaTCC.demo.usuario.Usuario;

public record ListarUsuarioRequest(Long id, String nickName, String imagemPerfil) {
    public ListarUsuarioRequest(Usuario usuario){
        this(usuario.getId(), usuario.getNickName(), usuario.getImagemPerfil());
    }
}
