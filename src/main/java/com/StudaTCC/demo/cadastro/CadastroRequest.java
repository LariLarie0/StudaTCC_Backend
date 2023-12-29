package com.StudaTCC.demo.cadastro;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CadastroRequest {
    @NotBlank
    private String nome;
    @NotBlank
    private String sobrenome;
    @NotBlank
    private String nickName;
    @NotBlank
    private String email;
    @NotBlank
    private String senha;
}
