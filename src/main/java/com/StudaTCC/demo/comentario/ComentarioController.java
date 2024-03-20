package com.StudaTCC.demo.comentario;

import com.StudaTCC.demo.material.MaterialService;
import com.StudaTCC.demo.comentario.DTO.AdicionarComentarioRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {

    @Autowired
    private MaterialService materialService;
    private ComentarioRepository comentarioRepository;
    private ComentarioService comentarioService;

    @PostMapping("/criar/{materialId}")
    @CrossOrigin
    public ResponseEntity<?> criarNovoComentario(@PathVariable("materialId") Long materialId,
                                                     @RequestBody @Valid AdicionarComentarioRequest dados) {

        Comentario comentarioSalvo = materialService.criarMaterialComentario(materialId, dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(comentarioSalvo);
    }

    @DeleteMapping("/deletar/{comentarioId}")
    public ResponseEntity<?> deletarMaterialComentario(@PathVariable("comentarioId") Long comentarioId, Long materialId) {
        materialService.deletarMaterialComentario(comentarioId, materialId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping("/listar/{materialId}")
//    public ResponseEntity<List<AvaliacaoRequest>> mediaAvaliacao(@PathVariable Long materialId){
//        List<AvaliacaoRequest> comentarios =
//    }
}