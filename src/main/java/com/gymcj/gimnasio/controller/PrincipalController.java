package com.gymcj.gimnasio.controller;

import java.util.Collections;
import java.util.List;

import com.gymcj.gimnasio.entity.Usuario;
import com.gymcj.gimnasio.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gymcj.gimnasio.entity.ClasesModel;
import com.gymcj.gimnasio.service.ClasesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PrincipalController {

    @Autowired
    private ClasesService clasesService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String register(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model) {

        if (usuarioService.existsByUsername(username)) {
            model.addAttribute("error", "El nombre de usuario ya existe");
            return "register";
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRoles(Collections.singleton("ROLE_USUARIO"));

        usuarioService.saveUser(usuario);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Credenciales incorrectas.");
        }
        return "login";
    }


    @GetMapping("/error/403")
    public String Error() {
        return "error/403";
    }

    @GetMapping("/")
    public String Index(Model model){
        List<ClasesModel> clases = clasesService.getClases();
        model.addAttribute("clases", clases);
        
        return "index";
    }
    
}
