package com.StudaTCC.demo.material;

import com.StudaTCC.demo.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    Material findMaterialByUsuario(Usuario usuario);
    Optional<Material> findById(Long materialId);
    void deleteById(Long materialId);
}
