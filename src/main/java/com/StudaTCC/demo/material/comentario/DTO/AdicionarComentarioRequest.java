package com.StudaTCC.demo.material.comentario.DTO;

import jakarta.validation.constraints.NotEmpty;

public record AdicionarComentarioRequest(
        @NotEmpty
        String texto
) {
}
