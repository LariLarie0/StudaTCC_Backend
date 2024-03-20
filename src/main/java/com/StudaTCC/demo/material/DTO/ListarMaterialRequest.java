package com.StudaTCC.demo.material.DTO;

import com.StudaTCC.demo.material.Material;

public record ListarMaterialRequest(
        Long id, String titulo,
        String descricao, int comentarioContagem,
        int likeContagem, String imagemMaterial) {

    public ListarMaterialRequest(Material material){
        this(material.getId(), material.getTitulo(),
             material.getDescricao(), material.getComentarioContagem(),
             material.getLikeContagem(), material.getImagemMaterial());
    }
}
