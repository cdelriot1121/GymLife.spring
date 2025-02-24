package com.gymcj.gimnasio.config;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gymcj.gimnasio.entity.Usuario;
import com.gymcj.gimnasio.repository.UsuarioRepository;

@Service
public class CustomerUserDetailService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomerUserDetailService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Intentando autenticar al usuario: ".concat(username));

        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> {
            System.out.println("Usuario no encontrado en la base de datos ".concat(username));
            return new UsernameNotFoundException("Usuario no encontrado");
        });

        System.out.println("Usuario encontrado ".concat(usuario.getUsername()));

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet())
        );
    }



}
