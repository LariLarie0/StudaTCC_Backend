package com.StudaTCC.demo.security;

import com.StudaTCC.demo.usuario.Usuario;
import com.StudaTCC.demo.usuario.UsuarioRepositorio;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJKT = recuperarToken(request);

        if(tokenJKT != null){
            var subject = tokenService.getSubject(tokenJKT);

            Usuario usuario = confereLoginUsuario(subject);

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }


        filterChain.doFilter(request, response);

    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null){
            return authorizationHeader.replace("Bearer ", "");

        }
        return null;
    }

    private Usuario confereLoginUsuario(String login){
        var usuarios = usuarioRepositorio.findAll();
        for(Usuario usu : usuarios){
            var email = usu.getEmail();
            if(login.equals(email)){
                return usu;
            }
        }
        return null;

    }
}
