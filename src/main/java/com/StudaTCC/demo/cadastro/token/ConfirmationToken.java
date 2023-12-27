package com.StudaTCC.demo.cadastro.token;

import com.StudaTCC.demo.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String token;

    @Getter
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Getter
    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Getter
    private LocalDateTime confirmedAt;

    @Getter
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "usuario_id"
    )
    private Usuario usuario;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, Usuario usuario) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.usuario = usuario;
    }
}
