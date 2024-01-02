package com.StudaTCC.demo.material.comentario;

import com.StudaTCC.demo.material.Material;
import com.StudaTCC.demo.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "materialComentario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String texto;

    @OneToOne
    @JoinColumn(name = "Usuario_id", nullable = false)
    private Usuario usuario;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;
}