package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Profesional;

public interface ProfesionalService {
    List<Profesional> obtenerTodosLosProfesionales();
    Profesional obtenerProfesionalPorId(Integer id);
    Profesional guardarProfesional(Profesional profesional);
    void eliminarProfesional(Integer id);
	Profesional obtenerServicioPorId(Integer profesionalId);
	List<Profesional> findAll();
	Optional<Profesional> findById(Integer id);
	void delete(Integer id);
	Profesional save(Profesional profesional);
}
