package com.StudaTCC.demo.comentario;

import com.StudaTCC.demo.comentario.DTO.AdicionarComentarioRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {
    @Autowired
    private ComentarioService comentarioService;

    @PostMapping("/criar/{materialId}")
    @Transactional
    public ResponseEntity<Comentario> criarNovoComentario(@PathVariable Long materialId,
                                                          @RequestBody AdicionarComentarioRequest dados) {

        Comentario comentarioSalvo = comentarioService.criarNovoComentario(materialId, dados);
        return ResponseEntity.ok(comentarioSalvo);
    }

    @GetMapping("/listar/{materialId}")
    public ResponseEntity<List<Comentario>> listComentarioByMaterial(@PathVariable Long materialId) {
        List<Comentario> comentarios = comentarioService.listComentarioByMaterial(materialId);
        return ResponseEntity.ok(comentarios);
    }

    @DeleteMapping("/deletar/{comentarioId}/{materialId}")
    public ResponseEntity<Comentario> deletarComentario(@PathVariable Long comentarioId,
                                                        @PathVariable Long materialId) {
        comentarioService.deletarComentario(comentarioId, materialId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}