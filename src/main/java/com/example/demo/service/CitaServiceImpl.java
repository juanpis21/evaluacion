package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.demo.model.Cita;
import com.example.demo.repository.CitaRepository;

import java.util.List;

@Service
@Primary
public class CitaServiceImpl implements CitaService {

	@Autowired
	private CitaRepository citaRepository;

	@Override
	public List<Cita> obtenerCitasPorUsuario(Integer usuarioId) {
		System.out.println("=== DEBUG: Buscando citas para usuario ID: " + usuarioId);
		List<Cita> citas = citaRepository.findByUsuarioId(usuarioId);
		System.out.println("=== DEBUG: Citas encontradas: " + citas.size());

		// Debug detallado de cada cita
		for (Cita cita : citas) {
			System.out.println("Cita ID: " + cita.getId() + ", Estado: " + cita.getEstado() + ", Servicio: "
					+ (cita.getServicio() != null ? cita.getServicio().getNombre() : "null") + ", Profesional: "
					+ (cita.getProfesional() != null ? cita.getProfesional().getUsuario().getNombre() : "null"));
		}

		return citas;
	}

	@Override
	public List<Cita> obtenerTodasLasCitas() {
		return citaRepository.findAll();
	}

	@Override
	public Cita guardarCita(Cita cita) {
		return citaRepository.save(cita);
	}

	@Override
	public void eliminarCita(Integer id) {
		citaRepository.deleteById(id);
	}
}