package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioApiController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/crear")
    public Map<String, Object> crearUsuario(@RequestBody Map<String, String> userData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String nombre = userData.get("nombre");
            String email = userData.get("email");
            String password = userData.get("password");
            String telefono = userData.get("telefono");
            if (nombre == null || email == null || password == null) {
                response.put("status", "error");
                response.put("message", "Nombre, email y password son obligatorios");
                return response;
            }
            if (usuarioService.existsByEmail(email)) {
                response.put("status", "error");
                response.put("message", "El email ya está registrado");
                return response;
            }
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(password);
            usuario.setTelefono(telefono);
            usuario.setFechaRegistro(LocalDateTime.now());
            Usuario usuarioGuardado = usuarioService.save(usuario);
            Map<String, Object> usuarioResponse = new HashMap<>();
            usuarioResponse.put("id", usuarioGuardado.getId());
            usuarioResponse.put("nombre", usuarioGuardado.getNombre());
            usuarioResponse.put("email", usuarioGuardado.getEmail());
            usuarioResponse.put("telefono", usuarioGuardado.getTelefono());
            usuarioResponse.put("fechaRegistro", usuarioGuardado.getFechaRegistro());

            response.put("status", "success");
            response.put("message", "Usuario creado correctamente");
            response.put("usuario", usuarioResponse);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error del servidor: " + e.getMessage());
        }
        
        return response;
    }

    @GetMapping("/listar")
    public Map<String, Object> listarUsuarios() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Usuario> usuarios = usuarioService.findAll();
            response.put("status", "success");
            response.put("usuarios", usuarios);
            response.put("total", usuarios.size());
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error al obtener usuarios");
        }
        
        return response;
    }
    @GetMapping("/{id}")
    public Map<String, Object> obtenerUsuario(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Usuario> usuario = usuarioService.findById(id);
            if (usuario.isPresent()) {
                response.put("status", "success");
                response.put("usuario", usuario.get());
            } else {
                response.put("status", "error");
                response.put("message", "Usuario no encontrado");
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error al obtener usuario");
        }
        
        return response;
    }

        @PutMapping("/{id}")
    public Map<String, Object> actualizarUsuario(@PathVariable Integer id, @RequestBody Map<String, String> userData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Usuario> usuarioExistente = usuarioService.findById(id);
            if (!usuarioExistente.isPresent()) {
                response.put("status", "error");
                response.put("message", "Usuario no encontrado");
                return response;
            }

            Usuario usuario = usuarioExistente.get();
            String nuevoNombre = userData.get("nombre");
            String nuevoEmail = userData.get("email");
            String nuevoTelefono = userData.get("telefono");

                        if (nuevoEmail != null && !usuario.getEmail().equals(nuevoEmail)) {
                if (usuarioService.existsByEmail(nuevoEmail)) {
                    response.put("status", "error");
                    response.put("message", "El email ya está registrado");
                    return response;
                }
                usuario.setEmail(nuevoEmail);
            }

            if (nuevoNombre != null) {
                usuario.setNombre(nuevoNombre);
            }
            if (nuevoTelefono != null) {
                usuario.setTelefono(nuevoTelefono);
            }

            Usuario usuarioActualizado = usuarioService.update(usuario);
            
            response.put("status", "success");
            response.put("message", "Usuario actualizado correctamente");
            response.put("usuario", usuarioActualizado);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error al actualizar usuario: " + e.getMessage());
        }
        
        return response;
    }

        @DeleteMapping("/{id}")
    public Map<String, Object> eliminarUsuario(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Usuario> usuario = usuarioService.findById(id);
            if (!usuario.isPresent()) {
                response.put("status", "error");
                response.put("message", "Usuario no encontrado");
                return response;
            }

            usuarioService.delete(id);
            response.put("status", "success");
            response.put("message", "Usuario eliminado correctamente");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error al eliminar usuario: " + e.getMessage());
        }
        
        return response;
    }

        @GetMapping("/email/{email}")
    public Map<String, Object> buscarPorEmail(@PathVariable String email) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Usuario> usuario = usuarioService.findByEmail(email);
            if (usuario.isPresent()) {
                response.put("status", "success");
                response.put("usuario", usuario.get());
            } else {
                response.put("status", "error");
                response.put("message", "Usuario no encontrado");
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error al buscar usuario");
        }
        
        return response;
    }
}