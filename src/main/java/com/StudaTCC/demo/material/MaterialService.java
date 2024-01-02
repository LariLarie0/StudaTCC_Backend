package com.StudaTCC.demo.material;

import com.StudaTCC.demo.material.DTO.AdicionarMaterialRequest;
import com.StudaTCC.demo.material.comentario.Comentario;
import com.StudaTCC.demo.material.comentario.ComentarioService;
import com.StudaTCC.demo.material.comentario.DTO.AdicionarComentarioRequest;
import com.StudaTCC.demo.usuario.Usuario;
import com.StudaTCC.demo.usuario.UsuarioService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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

    public Material getMaterialById(Long materialId) {
        return materialRepository.findById(materialId).orElseThrow(RuntimeException::new);
    }

    public Material adicionarNovoMaterial(AdicionarMaterialRequest dados) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Usuario usuario = null;

        if (auth != null && auth.isAuthenticated()) {
            Object principal = auth.getPrincipal();

            if (principal instanceof Usuario) {
                usuario = (Usuario) principal;
            }
        }


        Material novoMaterial = new Material();
        novoMaterial.setTitulo(dados.titulo());
        novoMaterial.setDescricao(dados.descricao());
        novoMaterial.setConteudo(dados.conteudo());
        novoMaterial.setImagemMaterial(dados.materialFoto());
//        novoMaterial.setSalvosLista(null);
        novoMaterial.setMaterialComentarios(null);
        novoMaterial.setUsuario(usuario);
        novoMaterial.setIsTypeShare(false);
        List<Ref> refs;
        if (dados.referencias() != null) {
            refs = dados.referencias().stream()
                    .map(url -> new Ref(novoMaterial, url))
                    .collect(Collectors.toList());
        } else {
            refs = Collections.emptyList();
        }
        novoMaterial.setRefs(refs);

        return materialRepository.save(novoMaterial);
    }

    @Transactional
    public void deletarMaterial(Long materialId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Usuario usuario = null;

        if (auth != null && auth.isAuthenticated()) {
            Object principal = auth.getPrincipal();

            if (principal instanceof Usuario) {
                usuario = (Usuario) principal;
            }
        }

        Material materialDeletar = getMaterialById(materialId);

        if (materialDeletar.getUsuario().getNickName().equals(usuario.getNickName())) {
//            materialDeletar.getSalvosLista().forEach(materialSalvo -> {
//                materialSalvo.setMaterialSalvo(null);
//                materialRepository.save(materialSalvo);
//            });
            materialRepository.deleteById(materialId);
        } else {
            throw new RuntimeException();
        }
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

    void deletarMaterialComentario(Long comentarioId, Long materialId) {
        Material material = getMaterialById(materialId);
        comentarioService.deletarComentario(comentarioId);
        material.setComentarioContagem(material.getComentarioContagem()-1);
        materialRepository.save(material);
    }

    Material adicionarMaterialSalvo(Long materialId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Usuario usuario = null;

        if (auth != null && auth.isAuthenticated()) {
            Object principal = auth.getPrincipal();

            if (principal instanceof Usuario) {
                usuario = (Usuario) principal;
            }
        }

        Material material = getMaterialById(materialId);
        if (!material.getIsTypeShare()) {
            Material novoMeterialSalvo = new Material();
            novoMeterialSalvo.setUsuario(usuario);
            novoMeterialSalvo.setIsTypeShare(true);
//            novoMeterialSalvo.setMaterialSalvo(material);
            Material salvoMaterialSalvo = materialRepository.save(novoMeterialSalvo);
//            material.getSalvosLista().add(salvoMaterialSalvo);
            material.setSalvosContagem(material.getSalvosContagem()+1);
            materialRepository.save(material);

            return salvoMaterialSalvo;
        } else {
            throw new RuntimeException();
        }
    }
    void deletarMaterialSalvo(Long materialSalvoId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Usuario usuario = null;

        if (auth != null && auth.isAuthenticated()) {
            Object principal = auth.getPrincipal();

            if (principal instanceof Usuario) {
                usuario = (Usuario) principal;
            }
        }

//        Material materialSalvo = getMaterialById(materialSalvoId);
//        if (materialSalvo.getUsuario().equals(usuario)) {
//            Material salvoMaterial = materialSalvo.getMaterialSalvo();
//            salvoMaterial.getSalvosLista().remove(materialSalvo);
//            salvoMaterial.setSalvosContagem(salvoMaterial.getSalvosContagem()-1);
//            materialRepository.save(salvoMaterial);
//            materialRepository.deleteById(materialSalvoId);
//
//        } else {
//            throw new RuntimeException();
//        }
    }
}
