package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario save(Usuario usuario) {
        // Si es nuevo usuario, asignamos fecha y contraseña
        if (usuario.getId() == null) {
            usuario.setFechaRegistro(LocalDateTime.now());
            // Transferir nuevaPassword a password
            if (usuario.getNuevaPassword() != null && !usuario.getNuevaPassword().isEmpty()) {
                usuario.setPassword(usuario.getNuevaPassword());
            }
        } else {
            // Si es actualización, solo cambiar contraseña si se llenó nuevaPassword
            Optional<Usuario> opt = usuarioRepository.findById(usuario.getId());
            if (opt.isPresent()) {
                Usuario existing = opt.get();
                if (usuario.getNuevaPassword() != null && !usuario.getNuevaPassword().isEmpty()) {
                    existing.setPassword(usuario.getNuevaPassword());
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
    public void update(Usuario usuario) {
        save(usuario); // Reutilizamos save para actualizar
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
	public Optional<Usuario> get(Integer usuarioId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
}
