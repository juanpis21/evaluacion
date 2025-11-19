package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Cita;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    
    @Query("SELECT c FROM Cita c WHERE c.usuario.id = :usuarioId")
    List<Cita> findByUsuarioId(Integer usuarioId);
    
    List<Cita> findByEstado(String estado);
    
    List<Cita> findByProfesionalId(Integer profesionalId);
    
    List<Cita> findByServicioId(Integer servicioId);
    
    @Query("SELECT c FROM Cita c WHERE c.fechaHora BETWEEN :startDate AND :endDate")
    List<Cita> findByFechaHoraBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT c FROM Cita c WHERE c.usuario.id = :usuarioId AND c.estado = :estado")
    List<Cita> findByUsuarioIdAndEstado(Integer usuarioId, String estado);
}