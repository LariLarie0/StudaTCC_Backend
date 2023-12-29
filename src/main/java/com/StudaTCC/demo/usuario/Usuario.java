package com.StudaTCC.demo.usuario;

import com.StudaTCC.demo.cadastro.token.ConfirmationToken;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Usuario implements UserDetails {

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConfirmationToken> tokens = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    private String sobrenome;
    @Column(unique = true)
    private String nickName;
    private String email;
    private String senha;
    @Column(columnDefinition = "TEXT")
    private String imagemPerfil = "https://img.icons8.com/ios/50/user--v1.png";

    @Enumerated(EnumType.STRING)
    private UsuarioRole usuarioRole;
    private Boolean locked = false;
    private Boolean enabled = false;

    public Usuario(String nome, String sobrenome, String nickName, String email, String senha, UsuarioRole usuarioRole) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.nickName = nickName;
        this.email = email;
        this.senha = senha;
        this.usuarioRole = usuarioRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(usuarioRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
