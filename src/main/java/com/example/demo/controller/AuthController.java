package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private HttpSession session; 

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email,@RequestParam String password,Model model) {
        Usuario usuario = usuarioService.findByEmail(email).orElse(null);
        if (usuario == null) {
            model.addAttribute("error", "El usuario no existe");
            return "login";
        }
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            model.addAttribute("error", "Contraseña incorrecta");
            return "login";
        }
        session.setAttribute("usuarioLogueado", usuario);

        return "redirect:/usuarios";
    }
    @GetMapping("/register")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }
    @PostMapping("/register")
    public String procesarRegistro(@ModelAttribute("usuario") Usuario usuario, Model model) {
        try {
            usuarioService.save(usuario);
            model.addAttribute("mensaje", "Usuario registrado correctamente. Inicia sesión.");
            model.addAttribute("usuario", new Usuario());
            return "register";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/logout")
    public String cerrarSesion() {
        session.invalidate();
        return "redirect:/auth/login?logout=true";
    }
}
