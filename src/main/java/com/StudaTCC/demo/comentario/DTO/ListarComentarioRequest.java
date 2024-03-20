package com.StudaTCC.demo.comentario.DTO;

import com.StudaTCC.demo.usuario.Usuario;

public record ListarComentarioRequest(Long id, String texto, Usuario usuario, Integer avaliacao) {
}

