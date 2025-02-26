package com.gymcj.gimnasio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gymcj.gimnasio.entity.ClasesModel;
import com.gymcj.gimnasio.service.ClasesService;

@Controller
public class UsuarioController {

    @Autowired
    private ClasesService clasesService;

    @GetMapping("/usuario/home")
    public String Usuarios(Model model){

        List<ClasesModel> clases = clasesService.getClases();
        model.addAttribute("clases", clases);

        return "usuarios";
    }

}
