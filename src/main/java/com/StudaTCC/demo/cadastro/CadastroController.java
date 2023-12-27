package com.StudaTCC.demo.cadastro;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.StudaTCC.demo.cadastro.CadastroService;

@RestController
@RequestMapping("/api/v1/cadastro")
@AllArgsConstructor
public class CadastroController {

    private CadastroService cadastroService;

    @PostMapping
    public String cadastro(@RequestBody CadastroRequest request) {
        return cadastroService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return cadastroService.confirmToken(token);
    }
}


