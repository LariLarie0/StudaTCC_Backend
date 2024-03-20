package com.StudaTCC.demo.material;

import com.StudaTCC.demo.material.DTO.AdicionarMaterialRequest;
import com.StudaTCC.demo.material.DTO.ListarMaterialRequest;
import com.StudaTCC.demo.comentario.Comentario;
import com.StudaTCC.demo.comentario.ComentarioService;
import com.StudaTCC.demo.comentario.DTO.AdicionarComentarioRequest;
import com.StudaTCC.demo.usuario.Usuario;
import com.StudaTCC.demo.usuario.UsuarioService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@RequiredArgsConstructor

public class MaterialService {
    private UsuarioService usuarioService;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private ComentarioService comentarioService;

    private Usuario getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.isAuthenticated() && auth.getPrincipal()
                instanceof Usuario) ? (Usuario) auth.getPrincipal() : null;
    }

    public Material getMaterialById(Long materialId) {
        return materialRepository.findById(materialId).orElseThrow(RuntimeException::new);
    }

    public Material getMaterialByUsuario(Usuario usuario) {
        return materialRepository.findMaterialByUsuario(usuario);
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

    public Comentario criarMaterialComentario(Long materialId, AdicionarComentarioRequest texto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Usuario usuario = null;

        if (auth != null && auth.isAuthenticated()) {
            Object principal = auth.getPrincipal();

            if (principal instanceof Usuario) {
                usuario = (Usuario) principal;
            }
        }

        Material material = materialRepository.getReferenceById(materialId);
        Comentario comentarioSalvo = comentarioService.criarNovoComentario(texto, material);
        material.setComentarioContagem(material.getComentarioContagem()+1);

        materialRepository.save(material);
        return comentarioSalvo;
    }

    public void deletarMaterialComentario(Long comentarioId, Long materialId) {
        Material material = getMaterialById(materialId);
        comentarioService.deletarComentario(comentarioId);
        material.setComentarioContagem(material.getComentarioContagem()-1);

        materialRepository.save(material);
    }

    public void likeMaterial (Long materialId) {
        Usuario usuario = getAuthenticatedUser();
        Material material = getMaterialById(materialId);

        if(!usuario.getCurtidos().contains(material)) {
            material.setLikeContagem(material.getLikeContagem()+1);
            material.getLikeList().add(usuario);

            materialRepository.save(material);
        } else {
            material.setLikeContagem(material.getLikeContagem()-1);
            material.getLikeList().remove(usuario);

            materialRepository.save(material);
        }
    }
}