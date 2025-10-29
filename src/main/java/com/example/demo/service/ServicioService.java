package com.example.demo.service;

import com.example.demo.model.Servicio;
import com.example.demo.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    public Optional<Servicio> get(Integer id) {
        return servicioRepository.findById(id);
    }

    public void save(Servicio servicio) {
        servicioRepository.save(servicio);
    }

    public void delete(Integer id) {
        servicioRepository.deleteById(id);
    }
}
