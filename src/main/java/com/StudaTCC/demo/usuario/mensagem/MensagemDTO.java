package com.StudaTCC.demo.usuario.mensagem;

public record MensagemDTO(String mensagem) {

    public MensagemDTO(MensagemResponse mensagem){
        this(mensagem.mensagem());
    }
}
