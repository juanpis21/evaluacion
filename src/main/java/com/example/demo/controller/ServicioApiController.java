package com.example.demo.controller;

import com.example.demo.model.Servicio;
import com.example.demo.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/servicios")
public class ServicioApiController {

    @Autowired
    private ServicioService servicioService;
    
    @PostMapping("/crear")
    public Map<String, Object> crearServicio(@RequestBody Map<String, Object> servicioData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String nombre = (String) servicioData.get("nombre");
            String descripcion = (String) servicioData.get("descripcion");
            Double precio = servicioData.get("precio") != null ? 
                Double.valueOf(servicioData.get("precio").toString()) : null;
            Integer duracion = servicioData.get("duracion") != null ? 
                Integer.valueOf(servicioData.get("duracion").toString()) : null;

            if (nombre == null || precio == null) {
                response.put("status", "error");
                response.put("message", "Nombre y precio son obligatorios");
                return response;
            }

            Servicio servicio = new Servicio();
            servicio.setNombre(nombre);
            servicio.setDescripcion(descripcion);
            servicio.setPrecio(precio);
            servicio.setDuracion(duracion);

            Servicio servicioGuardado = servicioService.save(servicio);
            
            response.put("status", "success");
            response.put("message", "Servicio creado correctamente");
            response.put("servicio", servicioGuardado);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error del servidor: " + e.getMessage());
        }
        
        return response;
    }
    @GetMapping("/listar")
    public Map<String, Object> listarServicios() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Servicio> servicios = servicioService.findAll();
            response.put("status", "success");
            response.put("servicios", servicios);
            response.put("total", servicios.size());
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error al obtener servicios");
        }
        
        return response;
    }

        @GetMapping("/{id}")
    public Map<String, Object> obtenerServicio(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Servicio> servicio = servicioService.findById(id);
            if (servicio.isPresent()) {
                response.put("status", "success");
                response.put("servicio", servicio.get());
            } else {
                response.put("status", "error");
                response.put("message", "Servicio no encontrado");
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error al obtener servicio");
        }
        
        return response;
    }

        @PutMapping("/{id}")
    public Map<String, Object> actualizarServicio(@PathVariable Integer id, @RequestBody Map<String, Object> servicioData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Servicio> servicioExistente = servicioService.findById(id);
            if (!servicioExistente.isPresent()) {
                response.put("status", "error");
                response.put("message", "Servicio no encontrado");
                return response;
            }

            Servicio servicio = servicioExistente.get();
            
                        if (servicioData.get("nombre") != null) {
                servicio.setNombre((String) servicioData.get("nombre"));
            }
            if (servicioData.get("descripcion") != null) {
                servicio.setDescripcion((String) servicioData.get("descripcion"));
            }
            if (servicioData.get("precio") != null) {
                servicio.setPrecio(Double.valueOf(servicioData.get("precio").toString()));
            }
            if (servicioData.get("duracion") != null) {
                servicio.setDuracion(Integer.valueOf(servicioData.get("duracion").toString()));
            }

            Servicio servicioActualizado = servicioService.save(servicio);
            
            response.put("status", "success");
            response.put("message", "Servicio actualizado correctamente");
            response.put("servicio", servicioActualizado);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error al actualizar servicio: " + e.getMessage());
        }
        
        return response;
    }

        @DeleteMapping("/{id}")
    public Map<String, Object> eliminarServicio(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Servicio> servicio = servicioService.findById(id);
            if (!servicio.isPresent()) {
                response.put("status", "error");
                response.put("message", "Servicio no encontrado");
                return response;
            }

            servicioService.delete(id);
            response.put("status", "success");
            response.put("message", "Servicio eliminado correctamente");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error al eliminar servicio: " + e.getMessage());
        }
        
        return response;
    }

        @GetMapping("/buscar")
    public Map<String, Object> buscarServiciosPorNombre(@RequestParam String nombre) {
        Map<String, Object> response = new HashMap<>();
        
        try {
                        List<Servicio> servicios = servicioService.findAll(); 
                        servicios.removeIf(s -> !s.getNombre().toLowerCase().contains(nombre.toLowerCase()));
            
            response.put("status", "success");
            response.put("servicios", servicios);
            response.put("total", servicios.size());
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error al buscar servicios");
        }
        
        return response;
    }
}