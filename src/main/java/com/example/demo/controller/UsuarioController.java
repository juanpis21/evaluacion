package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
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

    // Listar usuarios
    @GetMapping
    public String listarUsuarios(Model model, @RequestParam(value = "q", required = false) String query) {
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

    // Guardar o actualizar usuario
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttrs) {
        try {
            if (usuario.getId() == null) {
                // Nuevo usuario
                if (usuarioService.existsByEmail(usuario.getEmail())) {
                    redirectAttrs.addFlashAttribute("error", "El email ya está registrado");
                    return "redirect:/usuarios";
                }
                // Tomar contraseña del campo nuevaPassword
                if (usuario.getNuevaPassword() != null && !usuario.getNuevaPassword().isEmpty()) {
                    usuario.setPassword(usuario.getNuevaPassword());
                }
                usuarioService.save(usuario);
                redirectAttrs.addFlashAttribute("success", "Usuario creado correctamente");
            } else {
                // Actualizar usuario existente
                usuarioService.update(usuario);
                redirectAttrs.addFlashAttribute("success", "Usuario actualizado correctamente");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("error", "Error al guardar el usuario");
        }
        return "redirect:/usuarios";
    }

    // Eliminar usuario
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
