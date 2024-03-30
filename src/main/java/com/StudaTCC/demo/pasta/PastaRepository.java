package com.StudaTCC.demo.pasta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PastaRepository extends JpaRepository<Pasta, Long> {
    List<Pasta> findByUsuarioId(Long usuarioId);
}
