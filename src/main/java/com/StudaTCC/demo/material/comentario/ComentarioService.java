package com.StudaTCC.demo.material.comentario;

import com.StudaTCC.demo.material.Material;
import com.StudaTCC.demo.material.comentario.DTO.AdicionarComentarioRequest;
import com.StudaTCC.demo.usuario.Usuario;
import com.StudaTCC.demo.usuario.UsuarioRepository;
import com.StudaTCC.demo.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class ComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;
    private UsuarioService usuarioService;
    private UsuarioRepository usuarioRepository;

    public Comentario getComentarioById(Long comentarioId) {
        return comentarioRepository.findById(comentarioId).orElseThrow(RuntimeException::new);

    }

    public Comentario criarNovoComentario(AdicionarComentarioRequest dados, Material material) {
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = (Usuario) auth.getPrincipal();

            Comentario novoComentario = new Comentario();
            novoComentario.setTexto(dados.texto());
            novoComentario.setUsuario(usuario);
            novoComentario.setMaterial(material);

            return comentarioRepository.save(novoComentario);
        }catch (Exception ex){
            return null;
        }
    }

    public void deletarComentario(Long comentarioId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();

        Comentario comentario = getComentarioById(comentarioId);
        if (comentario.getUsuario().equals(usuario)) {
            comentarioRepository.deleteById(comentarioId);
        } else {
            throw new RuntimeException();
        }
    }
}
