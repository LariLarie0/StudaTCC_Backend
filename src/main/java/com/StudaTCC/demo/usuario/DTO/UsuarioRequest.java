package com.StudaTCC.demo.usuario.DTO;

import com.StudaTCC.demo.usuario.Usuario;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioRequest {
    private Long id;
    private String nickName;
}
