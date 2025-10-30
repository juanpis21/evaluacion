package com.example.demo.service;

import java.util.List;
import com.example.demo.model.Profesional;

public interface ProfesionalService {
    List<Profesional> obtenerTodosLosProfesionales();
    Profesional obtenerProfesionalPorId(Integer id);
    Profesional guardarProfesional(Profesional profesional);
    void eliminarProfesional(Integer id);
	Profesional obtenerServicioPorId(Integer profesionalId);
}
