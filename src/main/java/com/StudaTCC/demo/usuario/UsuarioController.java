package com.StudaTCC.demo.usuario;

import com.StudaTCC.demo.usuario.mensagem.MensagemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PutMapping("/editar/{usuarioId}")
    @Transactional
    public ResponseEntity<?> editarUsuario(@PathVariable Long usuarioId, @RequestBody EditUsuarioRequest dados){
        var response = usuarioService.editarUsuario(usuarioId, dados);
        return ResponseEntity.status(response.status()).body(new MensagemDTO(response));
    }

    @GetMapping("/buscarTodos")
    public ResponseEntity<List<ListarUsuarioRequest>> listarUsuarios(){
        var response = usuarioService.listarUsuarios();
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/deletar/{id}")
    @Transactional
    public ResponseEntity<MensagemDTO> deletarUsuario(@PathVariable Long id){
        var response = usuarioService.deletarUsuario(id);
        return ResponseEntity.status(response.status()).body(new MensagemDTO((response)));
    }
}
