package com.StudaTCC.demo.material.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record AdicionarMaterialRequest(
        String titulo,
        String descricao,
        String conteudo,
        List<String> referencias,
        String materialFoto
) {
}
