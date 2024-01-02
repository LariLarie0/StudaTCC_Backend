package com.StudaTCC.demo.usuario;

import com.StudaTCC.demo.usuario.DTO.EditUsuarioRequest;
import com.StudaTCC.demo.usuario.DTO.ListarUsuarioRequest;
import com.StudaTCC.demo.usuario.mensagem.MensagemResponse;
import com.StudaTCC.demo.cadastro.token.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
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

    public String signUsuario(Usuario usuario) {
        boolean userExists = usuarioRepository.findByEmail(usuario.getEmail()).isPresent();

        if (userExists) {
            throw new DuplicateKeyException("email already taken");
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

    @Override
    public UserDetails loadUserByUsername(String nickName) throws UsernameNotFoundException {
        return usuarioRepository.findByNickName(nickName). orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, nickName)));
    }

    public List<ListarUsuarioRequest> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(ListarUsuarioRequest::new).collect(Collectors.toList());
    }

    public MensagemResponse editarUsuario(Long usuarioId, EditUsuarioRequest dados){
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(()
            -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, usuarioId)));

        if(dados.getNome() != null && !dados.getNome().isEmpty()){
            usuario.setNome(dados.getNome());
        }
        if(dados.getSobrenome() != null && !dados.getSobrenome().isEmpty()){
            usuario.setSobrenome(dados.getSobrenome());
        }
        if(dados.getNickName() != null && !dados.getNickName().isEmpty()){
            usuario.setNickName(dados.getNickName());
        }
        if(dados.getEmail() != null && !dados.getEmail().isEmpty()){
            usuario.setEmail(dados.getEmail());
        }
        if(dados.getSenha() != null && !dados.getSenha().isEmpty()) {
            usuario.setSenha(bCryptPasswordEncoder.encode(dados.getSenha()));
        }
        if(dados.getImagemPerfil() != null && !dados.getImagemPerfil().isEmpty()){
            usuario.setImagemPerfil(dados.getImagemPerfil());
        }

        usuarioRepository.save(usuario);
        return new MensagemResponse("Usuario atualizado!", 200);
    }

    public MensagemResponse deletarUsuario(Long id){
     var usuario = usuarioRepository.getReferenceById(id);
        usuarioRepository.delete(usuario);
        return new MensagemResponse("Deletado com sucesso!", 200);
    }

    public int enableUsuario(String email) {
        return usuarioRepository.enableUsuario(email);
    }
}