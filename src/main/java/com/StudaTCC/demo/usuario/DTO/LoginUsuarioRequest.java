package com.StudaTCC.demo.usuario.DTO;

import com.StudaTCC.demo.cadastro.SenhaValidada;

public record LoginUsuarioRequest(String nickName, @SenhaValidada String senha){
}
