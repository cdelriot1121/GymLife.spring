package com.gymcj.gimnasio.config;


import com.gymcj.gimnasio.entity.Usuario;
import com.gymcj.gimnasio.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UsuarioRepository usuarioRepository;

    public CustomOAuth2UserService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");  // Obtenemos el correo del usuario

        Usuario usuario = usuarioRepository.findByUsername(email).orElse(null);

        if (usuario == null) {
            // ESTA ES LA CREACION DEL USUARIO CUANDO SE AUTENTICA POR OAuth2
            usuario = new Usuario();
            usuario.setUsername(email);
            usuario.setPassword(""); // OAuth no necesita password solo necesita que se guarde
            usuario.setRoles(new HashSet<>(Collections.singleton("ROLE_USUARIO")));

            usuarioRepository.save(usuario);
        }

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (String role : usuario.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "email");
    }

}
