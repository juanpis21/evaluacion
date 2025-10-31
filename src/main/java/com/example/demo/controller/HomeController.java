package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Redirige la raíz ("/") al login
    @GetMapping("/")
    public String index() {
        return "redirect:/auth/login";
    }
    @GetMapping("/home")
    public String home(Model model) {
        // Puedes pasar datos al modelo si quieres, por ejemplo el email del usuario
        // model.addAttribute("usuarioEmail", "ejemplo@correo.com");
        return "home"; // nombre de la vista Thymeleaf: home.html
    }
}

