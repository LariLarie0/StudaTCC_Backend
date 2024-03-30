package com.StudaTCC.demo.usuario;

import com.StudaTCC.demo.cadastro.token.ConfirmationToken;
import com.StudaTCC.demo.material.Material;
import com.StudaTCC.demo.pasta.Pasta;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
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
@Transactional
public class Usuario implements UserDetails {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConfirmationToken> tokens = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    private String sobrenome;
    @Column(unique = true)
    private String nickName;
    private String email;
    @JsonIgnore
    private String senha;
    @Column(columnDefinition = "TEXT")
    private String imagemPerfil = "https://img.icons8.com/ios/50/user--v1.png";

    @OneToMany(mappedBy = "usuario")
    private List<Material> materiais;

    @Enumerated(EnumType.STRING)
    private UsuarioRole usuarioRole;
    private Boolean locked = false;
    private Boolean enabled = false;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Material> curtidos = new ArrayList<>();

    @OneToMany(mappedBy="usuario")
    private List<Pasta> pastas;

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
        return nickName;
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
