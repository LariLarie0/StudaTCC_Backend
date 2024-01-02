package com.StudaTCC.demo.material.comentario;

import com.StudaTCC.demo.material.Material;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByMaterial(Material material, Pageable pageable);
}
