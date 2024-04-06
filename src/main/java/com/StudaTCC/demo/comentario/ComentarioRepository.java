package com.StudaTCC.demo.comentario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByMaterialId(Long materialId);
    Optional<Comentario> findById(Long comentarioId);
    void deleteById(Long comentarioId);
}