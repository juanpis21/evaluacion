package com.example.demo.service;

import java.util.List;
import com.example.demo.model.Cita;

public interface CitaService {
    List<Cita> obtenerCitasPorUsuario(Integer usuarioId);
    List<Cita> obtenerTodasLasCitas();
    Cita guardarCita(Cita cita);
    void eliminarCita(Integer id);
}