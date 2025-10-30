package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Cita;

import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    
    @Query("SELECT c FROM Cita c WHERE c.usuario.id = :usuarioId")
    List<Cita> findByUsuarioId(Integer usuarioId);
    
    List<Cita> findByEstado(String estado);
}