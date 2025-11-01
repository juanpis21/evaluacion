package com.example.demo.service;

import com.example.demo.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario save(Usuario usuario);

    // Actualizar usuario existente
    Usuario update(Usuario usuario);

    // Eliminar usuario por ID
    void delete(Integer id);

    // Buscar usuario por ID
    Optional<Usuario> findById(Integer id);

    // Buscar usuario por email
    Optional<Usuario> findByEmail(String email);

    // Listar todos los usuarios
    List<Usuario> findAll();

    // Buscar usuarios por nombre (contiene, case insensitive)
    List<Usuario> findByNombre(String nombre);

    // Verificar si un email ya existe
    boolean existsByEmail(String email);

    // Obtener el usuario actual logueado (según Spring Security)
    Usuario obtenerUsuarioActual();
}
