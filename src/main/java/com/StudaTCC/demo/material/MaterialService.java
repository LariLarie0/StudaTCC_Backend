package com.StudaTCC.demo.material;

import com.StudaTCC.demo.usuario.Usuario;
import com.StudaTCC.demo.comentario.Comentario;
import com.StudaTCC.demo.usuario.UsuarioRepository;
import com.StudaTCC.demo.material.DTO.ListarMaterialRequest;
import com.StudaTCC.demo.material.DTO.AdicionarMaterialRequest;

import org.hibernate.Hibernate;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Transactional
@RequiredArgsConstructor
public class MaterialService {
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.isAuthenticated() && auth.getPrincipal()
                instanceof Usuario) ? (Usuario) auth.getPrincipal() : null;
    }

    public Material getMaterialById(Long materialId) {
        return materialRepository.findById(materialId).orElseThrow(RuntimeException::new);
    }

    public Material adicionarNovoMaterial(AdicionarMaterialRequest dados) {
        Usuario usuario = getAuthenticatedUser();

        Material novoMaterial = new Material();
        novoMaterial.setTitulo(dados.titulo());
        novoMaterial.setDescricao(dados.descricao());
        novoMaterial.setConteudo(dados.conteudo());
        novoMaterial.setImagemMaterial(dados.materialFoto());
        novoMaterial.setMaterialComentarios(null);
        novoMaterial.setLikeList(null);
        novoMaterial.setUsuario(usuario);
        List<Ref> refs = (dados.referencias() != null) ? dados.referencias()
                .stream().map(url -> new Ref(novoMaterial, url))
                .collect(Collectors.toList()) : Collections.emptyList();
        novoMaterial.setRefs(refs);

        return materialRepository.save(novoMaterial);
    }

    @Transactional
    public void deletarMaterial(Long materialId) {
        Usuario usuario = getAuthenticatedUser();
        Material materialDeletar = getMaterialById(materialId);

        if (materialDeletar.getUsuario().getNickName().equals(usuario.getNickName())) {
            materialRepository.deleteById(materialId);
        } else {
            throw new RuntimeException();
        }
    }

    public List<ListarMaterialRequest> listarMateriais() {
        List<Material> materiais = materialRepository.findAll();
        return materiais.stream().map(ListarMaterialRequest::new).collect(Collectors.toList());
    }

    public List<Material> listMaterialByUsuario(Long usuarioId) {
        return materialRepository.findByUsuarioId(usuarioId);
    }

    public boolean likeMaterial (Long materialId) {
        Usuario usuario = getAuthenticatedUser();
        int i = 0;
        System.out.println(materialId);
        Hibernate.initialize(usuario.getCurtidos());

        for(Material curtido : usuario.getCurtidos()) {
            System.out.println((curtido.getId() == materialId) + " " + i);
            if(curtido.getId() == materialId) {
                usuario.getCurtidos().remove(i);
                curtido.getLikeList().removeIf(c -> c.getId() == usuario.getId());
                curtido.setLikeContagem(curtido.getLikeContagem() - 1);

                usuarioRepository.saveAndFlush(usuario);
                materialRepository.saveAndFlush(curtido);
                return false;
            }

            i++;
        }

        Material material = getMaterialById(materialId);

        usuario.getCurtidos().add(material);
        material.getLikeList().add(usuario);
        material.setLikeContagem(material.getLikeContagem() + 1);

        usuarioRepository.saveAndFlush(usuario);
        materialRepository.saveAndFlush(material);
        return true;
    }

    public Material notaMaterial(Long materialId) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new NoSuchElementException("Material não encontrado com o ID: " + materialId));

        List<Comentario> comentarios = material.getMaterialComentarios();
        if (comentarios.isEmpty()) {
            material.setAvaliacao(0);
        } else {
            double totalNotas = 0;
            for (Comentario comentario : comentarios) {
                totalNotas += comentario.getNota();
            }
            double mediaNotas = totalNotas / Double.valueOf(material.getComentarioContagem());

            String resultado = String.format("%.2f", mediaNotas);
            resultado = resultado.replace(",", ".");

            material.setAvaliacao(Double.parseDouble(resultado));
        }
        return materialRepository.save(material);
    }
}