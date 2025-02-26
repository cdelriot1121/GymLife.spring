package com.gymcj.gimnasio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrincipalController {
    @GetMapping("/login")
    public String Login(){
        return "login";
    }

    @GetMapping("/")
    public String Index(){
        return "index";
    }
    
    
}
