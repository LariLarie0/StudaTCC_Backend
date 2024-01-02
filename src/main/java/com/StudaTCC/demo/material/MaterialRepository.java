package com.StudaTCC.demo.material;

import com.StudaTCC.demo.usuario.Usuario;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findMaterialByUsuario(Usuario usuario, Pageable pageable);
    Optional<Material> findById(Long materialId);
    void deleteById(Long materialId);
}
