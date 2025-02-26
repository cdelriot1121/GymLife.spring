package com.gymcj.gimnasio.util;

import com.gymcj.gimnasio.entity.ClasesModel;
import com.gymcj.gimnasio.entity.Usuario;
import com.gymcj.gimnasio.repository.ClasesRepository;
import com.gymcj.gimnasio.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class DataLoader {

    @Bean
    public CommandLineRunner cargardatos(ClasesRepository clasesRepository, UsuarioRepository usuarioRepository) {
        return args -> {

            List<ClasesModel> clases = List.of(
                    new ClasesModel().builder()
                            .imagen("class-1.jpg")
                            .nombreClase("Entrenamiento de Fuerza")
                            .subClases("Entrenamiento de Resistencia")
                            .build(),

                    new ClasesModel().builder()
                            .imagen("class-2.jpg")
                            .nombreClase("Flexibilidad & Movilidad")
                            .subClases("Yoga y Pilates")
                            .build(),

                    new ClasesModel().builder()
                            .imagen("class-3.jpg")
                            .nombreClase("HIIT")
                            .subClases("Circuito de Entrenamiento")
                            .build(),

                    new ClasesModel().builder()
                            .imagen("class-4.jpg")
                            .nombreClase("Grupo Fitness")
                            .subClases("Zumba o Baile")
                            .build()
                    );

            clasesRepository.saveAll(clases);

            List<Usuario> usuarios = List.of(
                    new Usuario().builder()
                            .username("admin")
                            .password("$2a$10$5.bVloDkJJWqb7uL4QX1UeKH0Cnc3iu3oXJ/4E2kXq068KMAPbJHW")
                            .roles(Set.of("ROLE_ADMIN"))
                            .build(),

                    new Usuario().builder()
                            .username("usuario")
                            .password("$2a$10$8BiaIefaNDOyeJnBV/xMW./PXVhmBy4RotJro3JG4V7kaMURFvZ4m")
                            .roles(Set.of("ROLE_USUARIO"))
                            .build(),

                    new Usuario().builder()
                            .username("usuario1")
                            .password("$2a$10$Por.kmv1nYRcFCsjdv9uFO.jE.YzJ2784RuThZ8vjjY29VxvNfDsm")
                            .roles(Set.of("ROLE_USUARIO"))
                            .build(),

                    new Usuario().builder()
                            .username("usuario2")
                            .password("$2a$10$s139Yxk/UI42cWqZik0B5.ex9TPYqSHcy1vXg3.ySgjbcdS5vhNti")
                            .roles(Set.of("ROLE_USUARIO"))
                            .build(),

                    new Usuario().builder()
                            .username("usuario3")
                            .password("$2a$10$ZG/0RCuzWrPK7QKtyOdVrOBgvyRHM6tb8dF7.x/WQjamdiq8VUP.e")
                            .roles(Set.of("ROLE_USUARIO"))
                            .build(),

                    new Usuario().builder()
                            .username("usuario4")
                            .password("$2a$10$3C66yxeZmZzQWbyqxVBaD.IbLPNNhI82FFTNOI2udo9tfUm7V5zpm")
                            .roles(Set.of("ROLE_USUARIO"))
                            .build()
            );

            usuarioRepository.saveAll(usuarios);
        };
    }
}
