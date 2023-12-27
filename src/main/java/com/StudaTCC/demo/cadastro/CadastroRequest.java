package com.StudaTCC.demo.cadastro;

import com.StudaTCC.demo.usuario.Usuario;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CadastroRequest {
        private String nome;
        private String sobrenome;
        private String user;
        private String email;
        private String senha;
}
