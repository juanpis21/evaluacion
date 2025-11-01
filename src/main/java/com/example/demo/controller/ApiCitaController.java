package com.example.demo.controller;

import com.example.demo.model.Cita;
import com.example.demo.model.Servicio;
import com.example.demo.service.CitaService;
import com.example.demo.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/citas")
public class ApiCitaController {

    @Autowired
    private CitaService citaService;

    @GetMapping
    public List<Cita> getAll() {
        return citaService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Cita> getById(@PathVariable Integer id) {
        return citaService.findById(id);
    }

    @PostMapping
    public Cita create(@RequestBody Cita cita) {
        return citaService.save(cita);
    }

    @PutMapping("/{id}")
    public Cita update(@PathVariable Integer id, @RequestBody Cita cita) {
        cita.setId(id);
        return citaService.save(cita);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        citaService.delete(id);
    }
}
