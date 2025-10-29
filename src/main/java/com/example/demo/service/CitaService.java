package com.example.demo.service;

import com.example.demo.model.Cita;
import java.util.List;
import java.util.Optional;

public interface CitaService {
    List<Cita> findAll();
    Optional<Cita> get(Integer id);
    Cita save(Cita cita);
    void delete(Integer id);
}
