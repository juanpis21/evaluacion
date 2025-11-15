package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Cita;

public interface CitaService {
    List<Cita> obtenerCitasPorUsuario(Integer usuarioId);
    List<Cita> obtenerTodasLasCitas();
    Cita guardarCita(Cita cita);
    void eliminarCita(Integer id);
	List<Cita> findAll();
	Optional<Cita> findById(Integer id);
	Cita save(Cita cita);
	void delete(Integer id);
}