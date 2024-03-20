package com.StudaTCC.demo.material;

import com.StudaTCC.demo.material.DTO.AdicionarMaterialRequest;
import com.StudaTCC.demo.material.DTO.ListarMaterialRequest;
import com.StudaTCC.demo.usuario.DTO.ListarUsuarioRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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

    @PostMapping("/like/{materialId}")
    public ResponseEntity<?> likeMaterial(@PathVariable("materialId") Long materialId) {
        materialService.likeMaterial(materialId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}