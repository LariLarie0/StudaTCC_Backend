package com.StudaTCC.demo.comentario;

import com.StudaTCC.demo.material.Material;
import com.StudaTCC.demo.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "materialComentario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String texto;

    @ManyToOne
    @JoinColumn(name = "Usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    private Integer avaliacao;

    public Comentario(String texto, Usuario usuario, Material material, Integer avaliacao){
        this.texto = texto;
        this.usuario = usuario;
        this.material = material;
        this.avaliacao = avaliacao;
    }

    @JsonBackReference
    public Material getMaterial() {
        return material;
    }
    @JsonBackReference
    public void setMaterial() {
        this.material = material;
    }
}