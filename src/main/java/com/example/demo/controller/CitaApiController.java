package com.example.demo.controller;

import com.example.demo.model.Cita;
import com.example.demo.service.CitaService;
import com.example.demo.service.UsuarioService;
import com.example.demo.service.ProfesionalService;
import com.example.demo.service.ServicioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
public class CitaApiController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProfesionalService profesionalService;

    @Autowired
    private ServicioService servicioService;

    // ✅ GET - OBTENER TODAS LAS CITAS
    @GetMapping
    public ResponseEntity<?> getAllCitas() {
        try {
            List<Cita> citas = citaService.findAll();
            return ResponseEntity.ok(citas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al cargar las citas: " + e.getMessage());
        }
    }

    // ✅ GET - OBTENER CITA POR ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCitaById(@PathVariable Integer id) {
        try {
            Optional<Cita> cita = citaService.findById(id);
            if (cita.isPresent()) {
                return ResponseEntity.ok(cita.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Cita no encontrada con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar la cita: " + e.getMessage());
        }
    }

    // ✅ POST - CREAR NUEVA CITA
    @PostMapping
    public ResponseEntity<?> createCita(@RequestBody CitaRequest request) {
        try {
            // Validaciones
            if (request.getUsuarioId() == null || request.getProfesionalId() == null || 
                request.getServicioId() == null || request.getFechaHora() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Todos los campos son obligatorios.");
            }

            LocalDateTime fecha = LocalDateTime.parse(request.getFechaHora());
            
            if (fecha.isBefore(LocalDateTime.now())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("La fecha debe ser futura.");
            }

            Cita cita = new Cita();
            cita.setUsuario(usuarioService.findById(request.getUsuarioId()).orElse(null));
            cita.setProfesional(profesionalService.findById(request.getProfesionalId()).orElse(null));
            cita.setServicio(servicioService.findById(request.getServicioId()).orElse(null));
            cita.setFechaHora(fecha);
            cita.setEstado("pendiente");

            Cita savedCita = citaService.save(cita);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCita);

        } catch (DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Formato de fecha inválido. Use: YYYY-MM-DDTHH:MM:SS");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la cita: " + e.getMessage());
        }
    }

    // ✅ PUT - ACTUALIZAR CITA EXISTENTE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCita(@PathVariable Integer id, @RequestBody CitaRequest request) {
        try {
            Optional<Cita> citaExistente = citaService.findById(id);
            if (!citaExistente.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("La cita no existe.");
            }

            LocalDateTime fecha = LocalDateTime.parse(request.getFechaHora());
            
            if (fecha.isBefore(LocalDateTime.now())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("La fecha debe ser futura.");
            }

            Cita cita = citaExistente.get();
            cita.setUsuario(usuarioService.findById(request.getUsuarioId()).orElse(null));
            cita.setProfesional(profesionalService.findById(request.getProfesionalId()).orElse(null));
            cita.setServicio(servicioService.findById(request.getServicioId()).orElse(null));
            cita.setFechaHora(fecha);

            Cita updatedCita = citaService.save(cita);
            return ResponseEntity.ok(updatedCita);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar la cita: " + e.getMessage());
        }
    }

    // ✅ DELETE - ELIMINAR CITA
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCita(@PathVariable Integer id) {
        try {
            Optional<Cita> cita = citaService.findById(id);
            if (!cita.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("La cita no existe.");
            }
            
            citaService.delete(id);
            return ResponseEntity.ok().body("Cita eliminada correctamente.");
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la cita: " + e.getMessage());
        }
    }

    // ✅ PATCH - CAMBIAR ESTADO (ACEPTAR)
    @PatchMapping("/{id}/aceptar")
    public ResponseEntity<?> aceptarCita(@PathVariable Integer id) {
        return cambiarEstadoCita(id, "aceptada");
    }

    // ✅ PATCH - CAMBIAR ESTADO (RECHAZAR)
    @PatchMapping("/{id}/rechazar")
    public ResponseEntity<?> rechazarCita(@PathVariable Integer id) {
        return cambiarEstadoCita(id, "rechazada");
    }

    // ✅ PATCH - CAMBIAR ESTADO (COMPLETAR)
    @PatchMapping("/{id}/completar")
    public ResponseEntity<?> completarCita(@PathVariable Integer id) {
        return cambiarEstadoCita(id, "completada");
    }

    // ✅ MÉTODO PRIVADO PARA CAMBIAR ESTADO
    private ResponseEntity<?> cambiarEstadoCita(Integer id, String estado) {
        try {
            Optional<Cita> cita = citaService.findById(id);
            if (!cita.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("La cita no existe.");
            }

            Cita citaActualizada = cita.get();
            citaActualizada.setEstado(estado);
            Cita savedCita = citaService.save(citaActualizada);

            return ResponseEntity.ok(savedCita);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al cambiar el estado: " + e.getMessage());
        }
    }

    // ✅ CLASE INTERNA PARA EL REQUEST
    public static class CitaRequest {
        private Integer usuarioId;
        private Integer profesionalId;
        private Integer servicioId;
        private String fechaHora;

        // Getters y Setters
        public Integer getUsuarioId() { return usuarioId; }
        public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
        
        public Integer getProfesionalId() { return profesionalId; }
        public void setProfesionalId(Integer profesionalId) { this.profesionalId = profesionalId; }
        
        public Integer getServicioId() { return servicioId; }
        public void setServicioId(Integer servicioId) { this.servicioId = servicioId; }
        
        public String getFechaHora() { return fechaHora; }
        public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }
    }
}