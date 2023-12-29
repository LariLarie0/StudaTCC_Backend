package com.StudaTCC.demo.usuario;

import com.StudaTCC.demo.usuario.mensagem.MensagemResponse;
import com.StudaTCC.demo.cadastro.token.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.StudaTCC.demo.cadastro.token.ConfirmationTokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";

    private UsuarioRepository usuarioRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email). orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUsuario(Usuario usuario) {
        boolean userExists = usuarioRepository.findByEmail(usuario.getEmail()).isPresent();

        if (userExists) {
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(usuario.getPassword());
        usuario.setSenha(encodedPassword);
        usuarioRepository.save(usuario);
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                usuario
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public List<ListarUsuarioRequest> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(ListarUsuarioRequest::new).collect(Collectors.toList());
    }

    public MensagemResponse editarUsuario(Long usuarioId, EditUsuarioRequest dados){
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(()
            -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, usuarioId)));

        if(dados.nome() != null && !dados.nome().isEmpty()){
            usuario.setNome(dados.nome());
        }
        if(dados.sobrenome() != null && !dados.sobrenome().isEmpty()){
            usuario.setSobrenome(dados.sobrenome());
        }
        if(dados.nickName() != null && !dados.nickName().isEmpty()){
            usuario.setNickName(dados.nickName());
        }
        if(dados.email() != null && !dados.email().isEmpty()){
            usuario.setEmail(dados.email());
        }
        if(dados.senha() != null && !dados.senha().isEmpty()) {
            usuario.setSenha(bCryptPasswordEncoder.encode(dados.senha()));
        }
        if(dados.imagemPerfil() != null && !dados.imagemPerfil().isEmpty()){
            usuario.setImagemPerfil(dados.imagemPerfil());
        }

        usuarioRepository.save(usuario);
        return new MensagemResponse("Usuario atualizado!", 200);
    }

    public MensagemResponse deletarUsuario(Long id){
     var usuario = usuarioRepository.getReferenceById(id);
        usuarioRepository.delete(usuario);
        return new MensagemResponse("Deletado com sucesso!", 200);
    }

    public int enableAppUser(String email) {
        return usuarioRepository.enableUsuario(email);
    }
}