package com.StudaTCC.demo.comentario;

import com.StudaTCC.demo.material.Material;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByMaterial(Material material, Pageable pageable);
    @Query("SELECT c.avaliacao FROM Comentario c WHERE c.material.id = :materialId")
    Double findAvaliacaoByMaterialId(@Param("materialId") Long materialId);
    @Query("SELECT c FROM Comentario c WHERE c.material.id = :materialId")
    List<Comentario> consultarComentarioMaterial(Long materialId);
}