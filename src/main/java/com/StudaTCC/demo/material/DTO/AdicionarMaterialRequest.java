package com.StudaTCC.demo.material.DTO;

import java.util.List;

public record AdicionarMaterialRequest(
        String titulo,
        String descricao,
        String conteudo,
        List<String> referencias,
        String materialFoto
) {
}
