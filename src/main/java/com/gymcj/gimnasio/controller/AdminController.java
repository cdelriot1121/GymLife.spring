package com.gymcj.gimnasio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gymcj.gimnasio.entity.ClasesModel;
import com.gymcj.gimnasio.service.ClasesService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private ClasesService clasesService;
    
    @GetMapping("/admin/home")
    public String Admin(Model model){

        List<ClasesModel> clases = clasesService.getClases();
        model.addAttribute("clases", clases);

        return "admin";
    }

    @PostMapping("/admin/home")
    public String addClase(@RequestParam String imagen, @RequestParam String nombreClase, @RequestParam String subClases) {
        clasesService.addClase(imagen, nombreClase, subClases);
        return "redirect:/admin/home";
    }

    @PostMapping("/eliminar")
    public String addClase(@RequestParam("claseId") Long claseId) {
        clasesService.deleteClase(claseId);
        return "redirect:/admin/home";
    }

    @PostMapping("/actualizar")
    public String actualizarClase(@RequestParam("id") Long id, ClasesModel clasesActualizada) {
        clasesService.updateClase(id, clasesActualizada);
        return "redirect:/admin/home";
    }

    //Metodo para ver la lista de las clases con sus usuarios
    @GetMapping("admin/listar")
    public String Listar(Model model){
        List<ClasesModel> clases = clasesService.getClases();
        model.addAttribute("clases", clases);
        return "listar";
    }
}
