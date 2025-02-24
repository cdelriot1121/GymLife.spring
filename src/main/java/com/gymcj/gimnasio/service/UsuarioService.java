package com.gymcj.gimnasio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gymcj.gimnasio.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    public UsuarioRepository usuarioRepository;

}
