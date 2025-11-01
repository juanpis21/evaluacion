package com.example.demo.controller;


import com.example.demo.model.Profesional;
import com.example.demo.service.ProfesionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/profesionales")
public class ApiProfesionalController {

    @Autowired
    private ProfesionalService profesionalService;

    @GetMapping
    public List<Profesional> getAll() {
        return profesionalService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Profesional> getById(@PathVariable Integer id) {
        return profesionalService.findById(id);
    }

    @PostMapping
    public Profesional create(@RequestBody Profesional profesional) {
        return profesionalService.save(profesional);
    }

    @PutMapping("/{id}")
    public Profesional update(@PathVariable Integer id, @RequestBody Profesional profesional) {
        profesional.setId(id);
        return profesionalService.save(profesional);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        profesionalService.delete(id);
    }
}
