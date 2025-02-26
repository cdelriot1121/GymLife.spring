package com.gymcj.gimnasio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {

    @GetMapping("/usuario/home")
    public String Usuarios(){
        return "usuarios";
    }

}
