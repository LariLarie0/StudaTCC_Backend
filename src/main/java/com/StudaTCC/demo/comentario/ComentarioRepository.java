package com.StudaTCC.demo.comentario;

import com.StudaTCC.demo.material.Material;
import com.StudaTCC.demo.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByMaterialId(Long materialId);
    Optional<Comentario> findById(Long comentarioId);
    void deleteById(Long comentarioId);
    boolean existsByUsuarioAndMaterial(Usuario usuario, Material material);
}