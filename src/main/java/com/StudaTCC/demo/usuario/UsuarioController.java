package com.StudaTCC.demo.usuario;

import com.StudaTCC.demo.TokenRequest;
import com.StudaTCC.demo.security.TokenService;
import com.StudaTCC.demo.usuario.DTO.AutenticarRequest;
import com.StudaTCC.demo.usuario.DTO.EditUsuarioRequest;
import com.StudaTCC.demo.usuario.DTO.ListarUsuarioRequest;
import com.StudaTCC.demo.usuario.mensagem.MensagemDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity efetuarLogin(@RequestBody @Valid AutenticarRequest dados){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);
        TokenRequest tokenJWT = null;
        try{
            tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return ResponseEntity.ok(new TokenRequest(tokenJWT));
    }

    @PutMapping("/editar/{usuarioId}")
    @Transactional
    public ResponseEntity<?> editarUsuario(@PathVariable Long usuarioId, @RequestBody EditUsuarioRequest dados){
        var response = usuarioService.editarUsuario(usuarioId, dados);
        return ResponseEntity.status(response.status()).body(new MensagemDTO(response));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ListarUsuarioRequest>> listarUsuarios(){
        var response = usuarioService.listarUsuarios();
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/deletar/{id}")
    @Transactional
    public ResponseEntity<MensagemDTO> deletarUsuario(@PathVariable Long id){
        var response = usuarioService.deletarUsuario(id);
        return ResponseEntity.status(response.status()).body(new MensagemDTO((response)));
    }


}
