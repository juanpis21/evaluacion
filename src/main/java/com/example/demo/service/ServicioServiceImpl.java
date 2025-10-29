package com.example.demo.service;

import com.example.demo.model.Servicio;
import com.example.demo.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioServiceImpl {

    @Autowired
    private ServicioRepository servicioRepository;

    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    public Optional<Servicio> get(Integer id) {
        return servicioRepository.findById(id);
    }

    public Servicio save(Servicio servicio) {
        if (servicio.getId() == null) {
            // si es nuevo servicio, setear fecha de creación si la deseas (opcional)
            // servicio.setFechaCreacion(LocalDateTime.now());
        } else {
            // si quieres forzar que no se pierda algún dato existente, puedes cargar y actualizar campo por campo
            Optional<Servicio> opt = servicioRepository.findById(servicio.getId());
            if (opt.isPresent()) {
                Servicio existing = opt.get();
                existing.setNombre(servicio.getNombre());
                existing.setDescripcion(servicio.getDescripcion());
                existing.setPrecio(servicio.getPrecio());
                existing.setDuracion(servicio.getDuracion());
                return servicioRepository.save(existing);
            }
        }
        return servicioRepository.save(servicio);
    }

    public void delete(Integer id) {
        servicioRepository.deleteById(id);
    }

    // Opcional: búsqueda por nombre si tu repo la soporta
    public List<Servicio> findByNombre(String nombre) {
        try {
            // si el repo implementa findByNombreContainingIgnoreCase
            return servicioRepository.findByNombreContainingIgnoreCase(nombre);
        } catch (Throwable t) {
            // si no existe ese método en el repo, devolvemos todos (fallback)
            return servicioRepository.findAll();
        }
    }
}
