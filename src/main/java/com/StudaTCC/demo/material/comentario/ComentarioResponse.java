package com.StudaTCC.demo.material.comentario;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComentarioResponse {
    private Comentario comentario;
    private Boolean likedByAuthUser;
}
