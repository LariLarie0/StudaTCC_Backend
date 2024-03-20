package com.StudaTCC.demo.comentario.DTO;

import jakarta.validation.constraints.NotEmpty;

public record AdicionarComentarioRequest(
        Long materialId,
        Long usuarioId,
        @NotEmpty
        String texto,
        int avaliacao
){
}