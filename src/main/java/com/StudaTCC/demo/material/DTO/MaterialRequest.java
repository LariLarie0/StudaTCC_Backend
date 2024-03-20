package com.StudaTCC.demo.material.DTO;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class MaterialRequest {
    private Long idMaterial;
    private String titulo;
    private String descricao;
    private String conteudo;
    private String referencias;
}
