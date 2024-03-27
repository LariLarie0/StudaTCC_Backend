package com.StudaTCC.demo.material;

import com.StudaTCC.demo.comentario.Comentario;
import com.StudaTCC.demo.usuario.Usuario;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Transactional
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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
    @Column(columnDefinition = "TEXT")
    private String imagemMaterial = "https://img.icons8.com/ios/50/document--v1.png";

    private int comentarioContagem;
    private int likeContagem;

    @JsonIgnore
    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ref> refs;

    @JsonBackReference
    @JsonIgnore
    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    private List<Comentario> materialComentarios;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "material_likes",
            joinColumns = @JoinColumn(name = "material_id"),
            inverseJoinColumns = @JoinColumn(name = "liker_id")
    )
    private List<Usuario> likeList = new ArrayList<>();

    @JsonManagedReference
    @JsonIgnore
    public void setMaterialComentarios(List<Comentario> materialComentarios) {
        this.materialComentarios = materialComentarios;
    }
}