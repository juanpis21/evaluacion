package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listarUsuarios(Model model,@RequestParam(value = "q", required = false) String query,HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/auth/login";
        }
        List<Usuario> usuarios;
        if (query != null && !query.isEmpty()) {
            usuarios = usuarioService.findByNombre(query);
        } else {
            usuarios = usuarioService.findAll();
        }
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("query", query);
        return "usuarios";
    }
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttrs) {
        try {
            if (usuario.getId() == null) {
                // Nuevo usuario
                if (usuarioService.existsByEmail(usuario.getEmail())) {
                    redirectAttrs.addFlashAttribute("error", "El email ya está registrado");
                    return "redirect:/usuarios";
                }
            } else {
                usuarioService.findById(usuario.getId()).ifPresent(existing -> {
                    if (!existing.getEmail().equals(usuario.getEmail()) &&
                        usuarioService.existsByEmail(usuario.getEmail())) {
                        throw new RuntimeException("El email ya está registrado");
                    }
                });
            }
            usuarioService.save(usuario);
            redirectAttrs.addFlashAttribute("success", "Usuario guardado correctamente");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/usuarios";
    }
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id, RedirectAttributes redirectAttrs) {
        if (usuarioService.findById(id).isPresent()) {
            usuarioService.delete(id);
            redirectAttrs.addFlashAttribute("success", "Usuario eliminado correctamente");
        } else {
            redirectAttrs.addFlashAttribute("error", "Usuario no encontrado");
        }
        return "redirect:/usuarios";
    }
}
