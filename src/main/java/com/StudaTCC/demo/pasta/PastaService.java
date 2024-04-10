package com.StudaTCC.demo.pasta;

import com.StudaTCC.demo.material.Material;
import com.StudaTCC.demo.material.MaterialRepository;
import com.StudaTCC.demo.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@RequiredArgsConstructor
public class PastaService {
    @Autowired
    private PastaRepository pastaRepository;
    @Autowired
    private MaterialRepository materialRepository;

    private Usuario getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.isAuthenticated() && auth.getPrincipal()
                instanceof Usuario) ? (Usuario) auth.getPrincipal() : null;
    }

    public Pasta criarNovaPasta(String nome) {
        Usuario usuario = getAuthenticatedUser();

        if (usuario == null) {
            throw new RuntimeException("Usuário não autenticado.");
        }

        Pasta novaPasta = new Pasta();
        novaPasta.setNome(nome);
        novaPasta.setUsuario(usuario);

        return pastaRepository.save(novaPasta);
    }

    public List<Pasta> listPastaByUsuario(Long usuarioId) {
        return pastaRepository.findByUsuarioId(usuarioId);
    }

    public void adicionarMaterialNaPasta(Long pastaId, Long materialId) {
        Pasta pasta = pastaRepository.findById(pastaId)
                .orElseThrow(() -> new RuntimeException("Pasta não encontrada"));
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material não encontrado"));
        Usuario usuario = getAuthenticatedUser();

        if (usuario == null || !pasta.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Usuário não pode adicionar material nesta pasta");
        }

        if (pasta.getMateriais().contains(material)) {
            throw new RuntimeException("O material já foi adicionado a pasta");
        }

        pasta.getMateriais().add(material);
        pastaRepository.save(pasta);
    }



    public void removerMaterialDaPasta(Long pastaId, Long materialId) {
        Pasta pasta = pastaRepository.findById(pastaId)
                .orElseThrow(() -> new RuntimeException("Pasta não encontrada"));
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material não encontrado"));
        Usuario usuario = getAuthenticatedUser();

        if (usuario == null || !pasta.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Usuário não pode remover material desta pasta");
        }

        pasta.getMateriais().remove(material);
        pastaRepository.save(pasta);
    }

    public void excluirPasta(Long pastaId) {
        Pasta pasta = pastaRepository.findById(pastaId)
                .orElseThrow(() -> new RuntimeException("Pasta não encontrada."));
        Usuario usuario = getAuthenticatedUser();

        if (usuario == null || !pasta.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Usuário não pode excluir esta pasta");
        }

        pastaRepository.deleteById(pastaId);
    }
}