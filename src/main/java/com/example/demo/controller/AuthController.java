package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    // ✅ Vista de Login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    // ✅ Vista de Registro
    @GetMapping("/register")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    // ✅ Procesar registro
    @PostMapping("/register")
    public String procesarRegistro(@ModelAttribute("usuario") Usuario usuario, Model model) {
        // Verificar si el correo ya existe
        if (usuarioService.existsByEmail(usuario.getEmail())) {
            model.addAttribute("error", "El correo ya está registrado");
            return "register";
        }

        // Guardar usuario
        if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            model.addAttribute("error", "Debe ingresar una contraseña");
            return "register";
        }

        usuarioService.save(usuario);
        model.addAttribute("mensaje", "Registro exitoso. Ahora puedes iniciar sesión.");
        return "login";
    }

    // ✅ Procesar login
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email,
                                @RequestParam String password,
                                Model model) {
        Optional<Usuario> usuarioOpt = usuarioService.findByEmail(email);

        if (usuarioOpt.isPresent() && usuarioOpt.get().getPassword().equals(password)) {
            model.addAttribute("usuarioEmail", email);
            return "redirect:/auth/home?usuarioEmail=" + email;
        }

        model.addAttribute("error", "Credenciales incorrectas");
        return "login";
    }

    // ✅ Vista principal
    @GetMapping("/home")
    public String mostrarHome(@RequestParam(required = false) String usuarioEmail, Model model) {
        model.addAttribute("usuarioEmail", usuarioEmail);
        return "home";
    }
}
