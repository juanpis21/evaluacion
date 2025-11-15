package com.example.demo.controller;

import com.example.demo.model.Profesional;
import com.example.demo.service.ProfesionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/profesionales")
public class ProfesionalApiController {

    @Autowired
    private ProfesionalService profesionalService;

        @PostMapping("/crear")
    public Map<String, Object> crearProfesional(@RequestBody Map<String, String> profesionalData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String nombre = profesionalData.get("nombre");
            String especialidad = profesionalData.get("especialidad");
            String telefono = profesionalData.get("telefono");
            String email = profesionalData.get("email");
            String horarioDisponible = profesionalData.get("horarioDisponible");

                        if (nombre == null || especialidad == null || email == null) {
                response.put("status", "error");
                response.put("message", "Nombre, especialidad y email son obligatorios");
                return response;
            }

                        Profesional profesional = new Profesional();
            profesional.setNombre(nombre);
            profesional.setEspecialidad(especialidad);
            profesional.setTelefono(telefono);
            profesional.setEmail(email);
            profesional.setHorarioDisponible(horarioDisponible);

            Profesional profesionalGuardado = profesionalService.save(profesional);
            
            response.put("status", "success");
            response.put("message", "Profesional creado correctamente");
            response.put("profesional", profesionalGuardado);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error del servidor: " + e.getMessage());
        }
        
        return response;
    }
        @GetMapping("/listar")
    public Map<String, Object> listarProfesionales() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Profesional> profesionales = profesionalService.findAll();
            response.put("status", "success");
            response.put("profesionales", profesionales);
            response.put("total", profesionales.size());
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error al obtener profesionales");
        }
        
        return response;
    }

        @GetMapping("/{id}")
    public Map<String, Object> obtenerProfesional(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Profesional> profesional = profesionalService.findById(id);
            if (profesional.isPresent()) {
                response.put("status", "success");
                response.put("profesional", profesional.get());
            } else {
                response.put("status", "error");
                response.put("message", "Profesional no encontrado");
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error al obtener profesional");
        }
        
        return response;
    }

        @PutMapping("/{id}")
    public Map<String, Object> actualizarProfesional(@PathVariable Integer id, @RequestBody Map<String, String> profesionalData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Profesional> profesionalExistente = profesionalService.findById(id);
            if (!profesionalExistente.isPresent()) {
                response.put("status", "error");
                response.put("message", "Profesional no encontrado");
                return response;
            }

            Profesional profesional = profesionalExistente.get();
            
                        if (profesionalData.get("nombre") != null) {
                profesional.setNombre(profesionalData.get("nombre"));
            }
            if (profesionalData.get("especialidad") != null) {
                profesional.setEspecialidad(profesionalData.get("especialidad"));
            }
            if (profesionalData.get("telefono") != null) {
                profesional.setTelefono(profesionalData.get("telefono"));
            }
            if (profesionalData.get("email") != null) {
                profesional.setEmail(profesionalData.get("email"));
            }
            if (profesionalData.get("horarioDisponible") != null) {
                profesional.setHorarioDisponible(profesionalData.get("horarioDisponible"));
            }

            Profesional profesionalActualizado = profesionalService.save(profesional);
            
            response.put("status", "success");
            response.put("message", "Profesional actualizado correctamente");
            response.put("profesional", profesionalActualizado);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error al actualizar profesional: " + e.getMessage());
        }
        
        return response;
    }

        @DeleteMapping("/{id}")
    public Map<String, Object> eliminarProfesional(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Profesional> profesional = profesionalService.findById(id);
            if (!profesional.isPresent()) {
                response.put("status", "error");
                response.put("message", "Profesional no encontrado");
                return response;
            }

            profesionalService.delete(id);
            response.put("status", "success");
            response.put("message", "Profesional eliminado correctamente");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error al eliminar profesional: " + e.getMessage());
        }
        
        return response;
    }

        @GetMapping("/especialidad/{especialidad}")
    public Map<String, Object> obtenerPorEspecialidad(@PathVariable String especialidad) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Profesional> todosProfesionales = profesionalService.findAll();
                        List<Profesional> profesionalesFiltrados = todosProfesionales.stream()
                .filter(p -> p.getEspecialidad() != null && 
                           p.getEspecialidad().toLowerCase().contains(especialidad.toLowerCase()))
                .toList();
            
            response.put("status", "success");
            response.put("profesionales", profesionalesFiltrados);
            response.put("total", profesionalesFiltrados.size());
            response.put("especialidad", especialidad);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error al buscar profesionales por especialidad");
        }
        
        return response;
    }

        @GetMapping("/buscar")
    public Map<String, Object> buscarProfesionalesPorNombre(@RequestParam String nombre) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Profesional> todosProfesionales = profesionalService.findAll();
                        List<Profesional> profesionalesFiltrados = todosProfesionales.stream()
                .filter(p -> p.getNombre() != null && 
                           p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .toList();
            
            response.put("status", "success");
            response.put("profesionales", profesionalesFiltrados);
            response.put("total", profesionalesFiltrados.size());
            response.put("busqueda", nombre);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error al buscar profesionales");
        }
        
        return response;
    }
}