package com.StudaTCC.demo.pasta.DTO;

public record AdicionarMaterialNaPastaRequest(
        Long pastaId,
        Long materialId
) {}