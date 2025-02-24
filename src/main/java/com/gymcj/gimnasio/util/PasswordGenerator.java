package com.gymcj.gimnasio.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String passwordUsuario = "usuario";
        String encoderUsuario = passwordEncoder.encode(passwordUsuario);

        
        String passwordAdmin = "admin";
        String encoderAdmin = passwordEncoder.encode(passwordAdmin);

        System.out.println("La contraseña encriptada del usuario es: ".concat(encoderUsuario));
        System.out.println("La contraseña encriptada del admin es: ".concat(encoderAdmin));
    }
}
