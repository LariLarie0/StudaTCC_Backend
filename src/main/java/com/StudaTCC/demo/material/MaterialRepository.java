package com.StudaTCC.demo.material;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findByUsuarioId(Long usuarioId);
    Optional<Material> findById(Long materialId);
    void deleteById(Long materialId);
}
