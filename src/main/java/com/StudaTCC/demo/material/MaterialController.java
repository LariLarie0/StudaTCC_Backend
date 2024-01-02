package com.StudaTCC.demo.material;

import com.StudaTCC.demo.material.DTO.AdicionarMaterialRequest;
import com.StudaTCC.demo.material.comentario.Comentario;
import com.StudaTCC.demo.material.comentario.ComentarioResponse;
import com.StudaTCC.demo.material.comentario.DTO.AdicionarComentarioRequest;
import com.StudaTCC.demo.usuario.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.stringtemplate.v4.ST;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/materiais")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @PostMapping("/adicionar")
    public ResponseEntity<?> adicionarNovoMaterial(@RequestBody @Valid AdicionarMaterialRequest dados) {

        Material adicionarMaterial = materialService.adicionarNovoMaterial(dados);

        return ResponseEntity.ok(dados);
    }

    @DeleteMapping("/deletar/{materialId}")
    public ResponseEntity<?> deletarMaterial(@PathVariable("materialId") Long materialId) {
        materialService.deletarMaterial(materialId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/Salvar/{materialId}")
    public ResponseEntity<?> salvarMaterial(@PathVariable("materialId") Long materialId) {
        Material materialSalvo = materialService.adicionarMaterialSalvo(materialId);
        return new ResponseEntity<>(materialSalvo, HttpStatus.OK);
    }

    @PostMapping("/Salvar/desfazer/{materialSalvoId}")
    public ResponseEntity<?> deletePostShare(@PathVariable("materialSalvoId") Long materialSalvoId) {
        materialService.deletarMaterialSalvo(materialSalvoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/comentario/criar/{materialId}")
    public ResponseEntity<?> criarMaterialComentario(@PathVariable("materialId") Long materialId,
                                                     @RequestBody @Valid AdicionarComentarioRequest dados) {
        Comentario comentarioSalvo = materialService.criarMaterialComentario(materialId, dados);

        ComentarioResponse comentarioResponse = ComentarioResponse.builder()
                .comentario(comentarioSalvo)
                .likedByAuthUser(false)
                .build();
        return new ResponseEntity<>(comentarioResponse, HttpStatus.OK);
    }

    @PostMapping("/comentario/deletar/{materialId}/{comentarioId}")
    public ResponseEntity<?> deletarMaterialComentario(@PathVariable("comentarioId") Long comentarioId,
                                               @PathVariable("materialId") Long materialId) {
        materialService.deletarMaterialComentario(comentarioId, materialId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
