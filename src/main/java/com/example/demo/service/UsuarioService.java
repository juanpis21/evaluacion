package com.example.demo.service;

import com.example.demo.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario save(Usuario usuario);
    Usuario update(Usuario usuario);
    void delete(Integer id);
    Optional<Usuario> findById(Integer id);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findAll();
    List<Usuario> findByNombre(String nombre);
    boolean existsByEmail(String email);
    Usuario obtenerUsuarioActual();
}
