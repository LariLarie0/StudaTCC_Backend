package com.StudaTCC.demo.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Usuario a " + "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableUsuario(String email);

   Optional<Usuario> findByNickName(String nickName);
}
