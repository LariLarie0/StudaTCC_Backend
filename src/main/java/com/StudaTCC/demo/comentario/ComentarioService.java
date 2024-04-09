package com.StudaTCC.demo.comentario;

import com.StudaTCC.demo.comentario.DTO.AdicionarComentarioRequest;
import com.StudaTCC.demo.material.Material;
import com.StudaTCC.demo.material.MaterialRepository;
import com.StudaTCC.demo.material.MaterialService;
import com.StudaTCC.demo.usuario.Usuario;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Transactional
@RequiredArgsConstructor
public class ComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private MaterialService materialService;

    private Usuario getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof Usuario)
                ? (Usuario) auth.getPrincipal()
                : null;
    }

    public Material getMaterialById(Long materialId) {
        return materialRepository.findById(materialId).orElseThrow(RuntimeException::new);
    }

    public Comentario criarNovoComentario(Long materialId, AdicionarComentarioRequest dados) {
        Usuario usuario = getAuthenticatedUser();
        Material material = materialRepository.findById(materialId).orElseThrow(()
                -> new NoSuchElementException("Material não encontrado com o ID: " + materialId));

        Comentario novoComentario = new Comentario();
        novoComentario.setTexto(dados.texto());
        novoComentario.setNota(dados.nota());
        novoComentario.setUsuario(usuario);
        novoComentario.setMaterial(material);
        material.setComentarioContagem(material.getComentarioContagem()+1);
        comentarioRepository.save(novoComentario);
        materialService.notaMaterial(materialId);

        return novoComentario;
    }

    public List<Comentario> listComentarioByMaterial(Long materialId) {
        return comentarioRepository.findByMaterialId(materialId);
    }

    public void deletarComentario(Long comentarioId, Long materialId) {
        Usuario usuario = getAuthenticatedUser();
        Material material = getMaterialById(materialId);
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new NoSuchElementException("Comentário não encontrado com o ID: " + comentarioId));

        if (usuario == null || !comentario.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException();
        }

        comentarioRepository.delete(comentario);
        material.setComentarioContagem(material.getComentarioContagem()-1);
    }
}