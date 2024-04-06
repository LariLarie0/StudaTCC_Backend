package com.StudaTCC.demo.comentario;

import com.StudaTCC.demo.comentario.DTO.AdicionarComentarioRequest;
import com.StudaTCC.demo.material.Material;
import com.StudaTCC.demo.material.MaterialRepository;
import jakarta.validation.Valid;
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
    @Autowired
    private MaterialRepository materialRepository;

    @PostMapping("/criar/{materialId}")
    public ResponseEntity<Comentario> criarNovoComentario(@PathVariable Long materialId,
                                                          @RequestBody AdicionarComentarioRequest dados) {

        Comentario comentarioSalvo = comentarioService.criarNovoComentario(materialId, dados);
        return ResponseEntity.ok(comentarioSalvo);
    }

    @GetMapping("/listar/{materialId}")
    public ResponseEntity<List<Comentario>> listComentaroByMaterial(@PathVariable Long materialId) {
        List<Comentario> comentarios = comentarioService.listComentarioByMaterial(materialId);
        return ResponseEntity.ok(comentarios);
    }

    @DeleteMapping("/deletar/{comentarioId}")
    public ResponseEntity<Comentario> deletarComentario(@PathVariable Long comentarioId, Long materialId) {
        comentarioService.deletarComentario(comentarioId, materialId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}