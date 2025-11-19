package com.example.demo.service;

import java.time.LocalDateTime;
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
    List<Cita> findByEstado(String estado);
    List<Cita> findByProfesionalId(Integer profesionalId);
    List<Cita> findByServicioId(Integer servicioId);
    List<Cita> findByFechaHoraBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Cita> findByUsuarioIdAndEstado(Integer usuarioId, String estado);

    List<Cita> findCitasProximas(LocalDateTime fechaInicio);
    List<Cita> findCitasPorFecha(LocalDateTime fecha);
    boolean existeCitaEnMismoHorario(Integer profesionalId, LocalDateTime fechaHora);
    long contarCitasPorEstado(String estado);
    List<Cita> findCitasVencidas();
	List<Cita> findHistorial();
}