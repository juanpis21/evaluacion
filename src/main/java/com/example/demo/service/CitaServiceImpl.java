package com.example.demo.service;

import com.example.demo.model.Cita;
import com.example.demo.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Override
    public List<Cita> findAll() {
        return citaRepository.findAll();
    }

    @Override
    public Optional<Cita> get(Integer id) {
        return citaRepository.findById(id);
    }

    @Override
    public Cita save(Cita cita) {
        // Actualiza solo si ya existe
        if (cita.getId() != null) {
            Optional<Cita> existente = citaRepository.findById(cita.getId());
            if (existente.isPresent()) {
                Cita actual = existente.get();
                actual.setUsuario(cita.getUsuario());
                actual.setServicio(cita.getServicio());
                actual.setFechaHora(cita.getFechaHora());
                actual.setObservaciones(cita.getObservaciones());
                return citaRepository.save(actual);
            }
        }
        return citaRepository.save(cita);
    }

    @Override
    public void delete(Integer id) {
        citaRepository.deleteById(id);
    }
}
