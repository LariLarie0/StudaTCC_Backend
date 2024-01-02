package com.StudaTCC.demo.usuario.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditUsuarioRequest {
    private String nome;
    private String sobrenome;
    private String nickName;
    private String email;
    private String senha;
    private String imagemPerfil;
}
