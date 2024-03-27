package com.StudaTCC.demo.material;

import com.StudaTCC.demo.material.DTO.AdicionarMaterialRequest;
import com.StudaTCC.demo.material.DTO.ListarMaterialRequest;
import com.StudaTCC.demo.comentario.Comentario;
import com.StudaTCC.demo.comentario.ComentarioService;
import com.StudaTCC.demo.comentario.DTO.AdicionarComentarioRequest;
import com.StudaTCC.demo.usuario.Usuario;
import com.StudaTCC.demo.usuario.UsuarioRepository;
import com.StudaTCC.demo.usuario.UsuarioService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
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
}