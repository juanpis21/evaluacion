package com.example.demo.controller;

import com.example.demo.model.Servicio;
import com.example.demo.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/servicios")
public class ApiServicioController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping
    public List<Servicio> getAll() {
        return servicioService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Servicio> getById(@PathVariable Integer id) {
        return servicioService.findById(id);
    }

    @PostMapping
    public Servicio create(@RequestBody Servicio servicio) {
        return servicioService.save(servicio);
    }

    @PutMapping("/{id}")
    public Servicio update(@PathVariable Integer id, @RequestBody Servicio servicio) {
        servicio.setId(id);
        return servicioService.save(servicio);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        servicioService.delete(id);
    }
}
