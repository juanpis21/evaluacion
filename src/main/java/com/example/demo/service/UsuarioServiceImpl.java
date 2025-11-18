package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public Usuario save(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setFechaRegistro(LocalDateTime.now());

            if (usuario.getNuevaPassword() != null && !usuario.getNuevaPassword().isEmpty()) {
                usuario.setPassword(passwordEncoder.encode(usuario.getNuevaPassword()));
            }
        } else {
            Optional<Usuario> opt = usuarioRepository.findById(usuario.getId());
            if (opt.isPresent()) {
                Usuario existing = opt.get();

                if (usuario.getNuevaPassword() != null && !usuario.getNuevaPassword().isEmpty()) {
                    existing.setPassword(passwordEncoder.encode(usuario.getNuevaPassword()));
                }
                existing.setNombre(usuario.getNombre());
                existing.setEmail(usuario.getEmail());
                existing.setTelefono(usuario.getTelefono());
                return usuarioRepository.save(existing);
            }
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) {
        return save(usuario);
    }

    @Override
    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public List<Usuario> findByNombre(String nombre) {
        return usuarioRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public Usuario obtenerUsuarioActual() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            return usuarioRepository.findByEmail(email).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}
