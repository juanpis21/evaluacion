package com.example.demo.service;

import com.example.demo.model.Servicio;
import com.example.demo.repository.ServicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioServiceImpl(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }
    @Override
    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }
    @Override
    public Optional<Servicio> findById(Integer id) {
        return servicioRepository.findById(id);
    }
    @Override
    public Servicio save(Servicio servicio) {
        return servicioRepository.save(servicio);
    }
    @Override
    public void delete(Integer id) {
        servicioRepository.deleteById(id);
    }
}
