package com.StudaTCC.demo.pasta;

import com.StudaTCC.demo.comentario.Comentario;
import jakarta.validation.Valid;
import com.StudaTCC.demo.pasta.DTO.CriarNovaPastaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pastas")
public class PastaController {
    @Autowired
    private PastaService pastaService;

    @PostMapping("/criar")
    public ResponseEntity<Pasta> criarNovaPasta(@RequestBody @Valid CriarNovaPastaRequest dados) {
        Pasta pasta = pastaService.criarNovaPasta(dados.nome());
        return new ResponseEntity<>(pasta, HttpStatus.CREATED);
    }

    @GetMapping("/listar/{usuarioId}")
    public ResponseEntity<List<Pasta>> listPastaByUsuario(@PathVariable Long usuarioId) {
        List<Pasta> pastas = pastaService.listPastaByUsuario(usuarioId);
        return ResponseEntity.ok(pastas);
    }

    @PutMapping("/adicionar/{pastaId}/{materialId}")
    public ResponseEntity adicionarMaterialNaPasta(@PathVariable Long pastaId,
                                                   @PathVariable Long materialId) {
        pastaService.adicionarMaterialNaPasta(pastaId, materialId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/remover/{pastaId}/{materialId}")
    public ResponseEntity removerMaterialDaPasta(@PathVariable Long pastaId,
                                                 @PathVariable Long materialId) {
        pastaService.removerMaterialDaPasta(pastaId, materialId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deletar/{pastaId}")
    public ResponseEntity<Void> excluirPasta(@PathVariable Long pastaId) {
        pastaService.excluirPasta(pastaId);
        return ResponseEntity.ok().build();
    }
}