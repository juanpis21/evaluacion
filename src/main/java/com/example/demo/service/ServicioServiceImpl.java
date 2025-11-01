package com.example.demo.service;


import com.example.demo.model.Servicio;
import com.example.demo.repository.ServicioRepository;
import com.example.demo.service.ServicioService;
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
    public List<Servicio> obtenerTodosLosServicios() {
        return servicioRepository.findAll();
    }

    @Override
    public Servicio obtenerServicioPorId(Integer id) {
        Optional<Servicio> servicio = servicioRepository.findById(id);
        return servicio.orElse(null);
    }

    @Override
    public void guardarServicio(Servicio servicio) {
        servicioRepository.save(servicio);
    }

    @Override
    public void eliminarServicio(Integer id) {
        servicioRepository.deleteById(id);
    }
}
