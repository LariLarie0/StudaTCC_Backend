package com.StudaTCC.demo.material;

import com.StudaTCC.demo.material.comentario.Comentario;
import com.StudaTCC.demo.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private String titulo;
    @Column(columnDefinition = "VARCHAR(1000)")
    private String descricao;
    @Column(unique = true, columnDefinition = "LONGTEXT")
    private String conteudo;
    private String imagemMaterial;

    private int comentarioContagem = 0;
    private int salvosContagem;

    @Column(nullable = false)
    private Boolean isTypeShare;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ref> refs;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    private List<Comentario> materialComentarios = new ArrayList<>();

}