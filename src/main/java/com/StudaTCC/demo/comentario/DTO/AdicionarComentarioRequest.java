package com.StudaTCC.demo.comentario.DTO;

import jakarta.validation.constraints.NotEmpty;

public record AdicionarComentarioRequest(
        @NotEmpty
        String texto,
        @NotEmpty
        double nota
){
}