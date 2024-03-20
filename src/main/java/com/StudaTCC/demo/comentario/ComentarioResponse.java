package com.StudaTCC.demo.comentario;

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