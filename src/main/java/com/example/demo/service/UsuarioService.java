package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Usuario;

public interface UsuarioService {
    Usuario save(Usuario usuario);
    void update(Usuario usuario);
    void delete(Integer id);
    Optional<Usuario> findById(Integer id);
    List<Usuario> findAll();
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Usuario> findByNombre(String nombre);
	Optional<Usuario> get(Integer usuarioId);
}