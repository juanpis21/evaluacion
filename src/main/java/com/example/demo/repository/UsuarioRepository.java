package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Usuario;

import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
   
    
    Optional<Usuario> findByEmail(String email);
    
    /**
     * Verificar si existe un usuario con el email dado
     */
    boolean existsByEmail(String email);
    
    /**
     * Buscar usuarios por nombre (búsqueda parcial, insensible a mayúsculas)
     */
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
}