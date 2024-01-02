package com.StudaTCC.demo.usuario.DTO;

import jakarta.validation.constraints.NotBlank;

public record AutenticarRequest(
        @NotBlank
        String login,
        @NotBlank
        String senha) {
}
