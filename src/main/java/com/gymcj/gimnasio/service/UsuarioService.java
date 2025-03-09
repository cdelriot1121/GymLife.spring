package com.gymcj.gimnasio.service;

import com.gymcj.gimnasio.entity.ClasesModel;
import com.gymcj.gimnasio.entity.Usuario;
import com.gymcj.gimnasio.repository.ClasesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gymcj.gimnasio.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    public UsuarioRepository usuarioRepository;

    @Autowired
    public ClasesRepository clasesRepository;

    public void registrarClaseUsuario(String username, Long claseId) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("El usuario de nombre " + username + " no existe o fue eliminado"));

        ClasesModel clase = clasesRepository.findById(claseId)
                .orElseThrow(() -> new EntityNotFoundException("La clase con el id " + claseId + " no existe o ha sido eliminada"));

        usuario.getClases().add(clase);

        usuarioRepository.save(usuario);
    }

    public boolean existsByUsername(String username) {
        return usuarioRepository.findByUsername(username).isPresent();
    }

    public void saveUser(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

}
