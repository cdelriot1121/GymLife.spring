package com.gymcj.gimnasio.controller;

import java.util.List;

import com.gymcj.gimnasio.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gymcj.gimnasio.entity.ClasesModel;
import com.gymcj.gimnasio.service.ClasesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClasesService clasesService;

    @GetMapping("/usuario/home")
    public String Usuarios(Model model){

        List<ClasesModel> clases = clasesService.getClases();
        model.addAttribute("clases", clases);

        return "usuarios";
    }

    @PostMapping("/registroclases")
    public String RegistroClase(@RequestParam("claseId") Long claseId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String usuario = authentication.getName();

        usuarioService.registrarClaseUsuario(usuario, claseId);
        return "redirect:/usuario/home";
    }

}
