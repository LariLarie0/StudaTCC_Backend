package com.StudaTCC.demo.material;

import com.StudaTCC.demo.material.DTO.AdicionarMaterialRequest;
import com.StudaTCC.demo.material.DTO.ListarMaterialRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/listar")
    public ResponseEntity<List<ListarMaterialRequest>> listarMateriais(){
        var response = materialService.listarMateriais();
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/listar/{usuarioId}")
    public ResponseEntity<List<Material>> listMaterialByUsuario(@PathVariable Long usuarioId) {
        List<Material> materiais = materialService.listMaterialByUsuario(usuarioId);
        return ResponseEntity.ok(materiais);
    }

    @PostMapping("/like/{materialId}")
    public ResponseEntity<?> likeMaterial(@PathVariable("materialId") Long materialId) {
        try {
            boolean liked = materialService.likeMaterial(materialId);
            if (liked) {
                return ResponseEntity.ok("Material curtido com sucesso!");

            } else {
                return ResponseEntity.ok("Curtida removida com sucesso!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao realizar a ação no material:" + e.getMessage());
        }
    }
}