package com.example.demo.controller;

import com.example.demo.model.Cita;
import com.example.demo.model.Usuario;
import com.example.demo.model.Profesional;
import com.example.demo.model.Servicio;
import com.example.demo.service.CitaService;
import com.example.demo.service.UsuarioService;
import com.example.demo.service.ProfesionalService;
import com.example.demo.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    // ✅ ENDPOINT DE PRUEBA
    @GetMapping("/test")
    public Map<String, String> test() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "✅ API Citas FUNCIONANDO!");
        return response;
    }

    // ✅ CREAR CITA
    @PostMapping("/crear")
    public Map<String, Object> crearCita(@RequestBody Map<String, Object> citaData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Integer usuarioId = citaData.get("usuarioId") != null ? 
                Integer.valueOf(citaData.get("usuarioId").toString()) : null;
            Integer profesionalId = citaData.get("profesionalId") != null ? 
                Integer.valueOf(citaData.get("profesionalId").toString()) : null;
            Integer servicioId = citaData.get("servicioId") != null ? 
                Integer.valueOf(citaData.get("servicioId").toString()) : null;
            String fechaHora = (String) citaData.get("fechaHora");

            // Validar campos requeridos
            if (usuarioId == null || profesionalId == null || servicioId == null || fechaHora == null) {
                response.put("status", "error");
                response.put("message", "Todos los campos son obligatorios: usuarioId, profesionalId, servicioId, fechaHora");
                return response;
            }

            // Verificar que existan las entidades relacionadas
            Optional<Usuario> usuario = usuarioService.findById(usuarioId);
            Optional<Profesional> profesional = profesionalService.findById(profesionalId);
            Optional<Servicio> servicio = servicioService.findById(servicioId);

            if (!usuario.isPresent()) {
                response.put("status", "error");
                response.put("message", "Usuario no encontrado");
                return response;
            }
            if (!profesional.isPresent()) {
                response.put("status", "error");
                response.put("message", "Profesional no encontrado");
                return response;
            }
            if (!servicio.isPresent()) {
                response.put("status", "error");
                response.put("message", "Servicio no encontrado");
                return response;
            }

            // Crear nueva cita
            Cita cita = new Cita();
            cita.setUsuario(usuario.get());
            cita.setProfesional(profesional.get());
            cita.setServicio(servicio.get());
            cita.setFechaHora(LocalDateTime.parse(fechaHora));
            cita.setEstado("pendiente"); // Estado por defecto

            // Guardar cita
            Cita citaGuardada = citaService.save(cita);
            
            response.put("status", "success");
            response.put("message", "Cita creada correctamente");
            response.put("cita", citaGuardada);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error del servidor: " + e.getMessage());
        }
        
        return response;
    }

    // ✅ LISTAR TODAS LAS CITAS
    @GetMapping("/listar")
    public Map<String, Object> listarCitas() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Cita> citas = citaService.findAll();
            response.put("status", "success");
            response.put("citas", citas);
            response.put("total", citas.size());
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error al obtener citas");
        }
        
        return response;
    }

    // ✅ OBTENER CITA POR ID
    @GetMapping("/{id}")
    public Map<String, Object> obtenerCita(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Cita> cita = citaService.findById(id);
            if (cita.isPresent()) {
                response.put("status", "success");
                response.put("cita", cita.get());
            } else {
                response.put("status", "error");
                response.put("message", "Cita no encontrada");
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error al obtener cita");
        }
        
        return response;
    }

    // ✅ OBTENER CITAS POR USUARIO
    @GetMapping("/usuario/{usuarioId}")
    public Map<String, Object> obtenerCitasPorUsuario(@PathVariable Integer usuarioId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Cita> citas = citaService.obtenerCitasPorUsuario(usuarioId);
            response.put("status", "success");
            response.put("citas", citas);
            response.put("total", citas.size());
            response.put("usuarioId", usuarioId);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error al obtener citas del usuario");
        }
        
        return response;
    }

    // ✅ ACTUALIZAR CITA (PUT)
    @PutMapping("/{id}")
    public Map<String, Object> actualizarCita(@PathVariable Integer id, @RequestBody Map<String, Object> citaData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Cita> citaExistente = citaService.findById(id);
            if (!citaExistente.isPresent()) {
                response.put("status", "error");
                response.put("message", "Cita no encontrada");
                return response;
            }

            Cita cita = citaExistente.get();
            
            // Actualizar campos si están presentes
            if (citaData.get("fechaHora") != null) {
                cita.setFechaHora(LocalDateTime.parse(citaData.get("fechaHora").toString()));
            }
            if (citaData.get("estado") != null) {
                cita.setEstado(citaData.get("estado").toString());
            }
            if (citaData.get("profesionalId") != null) {
                Integer profesionalId = Integer.valueOf(citaData.get("profesionalId").toString());
                Optional<Profesional> profesional = profesionalService.findById(profesionalId);
                if (profesional.isPresent()) {
                    cita.setProfesional(profesional.get());
                }
            }
            if (citaData.get("servicioId") != null) {
                Integer servicioId = Integer.valueOf(citaData.get("servicioId").toString());
                Optional<Servicio> servicio = servicioService.findById(servicioId);
                if (servicio.isPresent()) {
                    cita.setServicio(servicio.get());
                }
            }

            Cita citaActualizada = citaService.save(cita);
            
            response.put("status", "success");
            response.put("message", "Cita actualizada correctamente");
            response.put("cita", citaActualizada);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error al actualizar cita: " + e.getMessage());
        }
        
        return response;
    }

    // ✅ ELIMINAR CITA (DELETE)
    @DeleteMapping("/{id}")
    public Map<String, Object> eliminarCita(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Cita> cita = citaService.findById(id);
            if (!cita.isPresent()) {
                response.put("status", "error");
                response.put("message", "Cita no encontrada");
                return response;
            }

            citaService.delete(id);
            response.put("status", "success");
            response.put("message", "Cita eliminada correctamente");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error al eliminar cita: " + e.getMessage());
        }
        
        return response;
    }

    // ✅ CAMBIAR ESTADO DE CITA
    @PutMapping("/{id}/estado")
    public Map<String, Object> cambiarEstadoCita(@PathVariable Integer id, @RequestBody Map<String, String> estadoData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Cita> citaExistente = citaService.findById(id);
            if (!citaExistente.isPresent()) {
                response.put("status", "error");
                response.put("message", "Cita no encontrada");
                return response;
            }

            String nuevoEstado = estadoData.get("estado");
            if (nuevoEstado == null) {
                response.put("status", "error");
                response.put("message", "El campo 'estado' es obligatorio");
                return response;
            }

            Cita cita = citaExistente.get();
            cita.setEstado(nuevoEstado);

            Cita citaActualizada = citaService.save(cita);
            
            response.put("status", "success");
            response.put("message", "Estado de cita actualizado correctamente");
            response.put("cita", citaActualizada);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error al cambiar estado de cita: " + e.getMessage());
        }
        
        return response;
    }

    // ✅ OBTENER CITAS POR ESTADO
    @GetMapping("/estado/{estado}")
    public Map<String, Object> obtenerCitasPorEstado(@PathVariable String estado) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Cita> todasCitas = citaService.findAll();
            // Filtrar por estado
            List<Cita> citasFiltradas = todasCitas.stream()
                .filter(c -> c.getEstado() != null && 
                           c.getEstado().equalsIgnoreCase(estado))
                .toList();
            
            response.put("status", "success");
            response.put("citas", citasFiltradas);
            response.put("total", citasFiltradas.size());
            response.put("estado", estado);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error al obtener citas por estado");
        }
        
        return response;
    }
}