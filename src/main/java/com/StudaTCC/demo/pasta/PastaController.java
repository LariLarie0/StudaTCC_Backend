package com.StudaTCC.demo.pasta;

import com.StudaTCC.demo.pasta.DTO.AdicionarMaterialNaPastaRequest;
import com.StudaTCC.demo.pasta.DTO.ExcluirPastaRequest;
import com.StudaTCC.demo.pasta.DTO.RemoverMaterialDaPastaRequest;
import jakarta.validation.Valid;
import com.StudaTCC.demo.pasta.DTO.CriarNovaPastaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/adicionar/{pastaId}/{materialId}")
    public ResponseEntity<Void> adicionarMaterialNaPasta(@RequestBody @Valid AdicionarMaterialNaPastaRequest dados) {
        pastaService.adicionarMaterialNaPasta(dados.pastaId(), dados.materialId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/remover/{pastaId}/{materialId}")
    public ResponseEntity<Void> removerMaterialDaPasta(@RequestBody @Valid RemoverMaterialDaPastaRequest dados) {
        pastaService.removerMaterialDaPasta(dados.pastaId(), dados.materialId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deletar/{pastaId}")
    public ResponseEntity<Void> excluirPasta(@RequestBody @Valid ExcluirPastaRequest dados) {
        pastaService.excluirPasta(dados.pastaId());
        return ResponseEntity.ok().build();
    }
}