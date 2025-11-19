package com.example.demo.controller;

import com.example.demo.model.Cita;
import com.example.demo.service.CitaService;
import com.example.demo.service.UsuarioService;
import com.example.demo.service.ProfesionalService;
import com.example.demo.service.ServicioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProfesionalService profesionalService;

    @Autowired
    private ServicioService servicioService;

    // ✅ LISTADO PRINCIPAL
    @GetMapping
    public String listarCitas(Model model) {
        try {
            model.addAttribute("citas", citaService.findAll());
            model.addAttribute("usuarios", usuarioService.findAll());
            model.addAttribute("profesionales", profesionalService.findAll());
            model.addAttribute("servicios", servicioService.findAll());
            return "citas";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar las citas: " + e.getMessage());
            return "citas";
        }
    }

    // ✅ GUARDAR NUEVA CITA
    @PostMapping("/guardar")
    public String guardarCita(
            @RequestParam Integer usuarioId,
            @RequestParam Integer profesionalId,
            @RequestParam Integer servicioId,
            @RequestParam String fechaHora,
            RedirectAttributes redirectAttributes) {

        try {
            // Validaciones
            if (usuarioId == null || profesionalId == null || servicioId == null || 
                fechaHora == null || fechaHora.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Todos los campos son obligatorios.");
                return "redirect:/citas";
            }

            LocalDateTime fecha = LocalDateTime.parse(fechaHora);
            
            if (fecha.isBefore(LocalDateTime.now())) {
                redirectAttributes.addFlashAttribute("error", "La fecha debe ser futura.");
                return "redirect:/citas";
            }

            Cita cita = new Cita();
            cita.setUsuario(usuarioService.findById(usuarioId).orElse(null));
            cita.setProfesional(profesionalService.findById(profesionalId).orElse(null));
            cita.setServicio(servicioService.findById(servicioId).orElse(null));
            cita.setFechaHora(fecha);
            cita.setEstado("pendiente");

            citaService.save(cita);
            redirectAttributes.addFlashAttribute("success", "Cita creada correctamente.");

        } catch (DateTimeParseException e) {
            redirectAttributes.addFlashAttribute("error", "Formato de fecha inválido.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear la cita: " + e.getMessage());
        }

        return "redirect:/citas";
    }

    // ✅ ACTUALIZAR CITA EXISTENTE
    @PostMapping("/editar")
    public String actualizarCita(
            @RequestParam Integer id,
            @RequestParam Integer usuarioId,
            @RequestParam Integer profesionalId,
            @RequestParam Integer servicioId,
            @RequestParam String fechaHora,
            RedirectAttributes redirectAttributes) {

        try {
            Optional<Cita> citaExistente = citaService.findById(id);
            if (!citaExistente.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "La cita no existe.");
                return "redirect:/citas";
            }

            LocalDateTime fecha = LocalDateTime.parse(fechaHora);
            
            if (fecha.isBefore(LocalDateTime.now())) {
                redirectAttributes.addFlashAttribute("error", "La fecha debe ser futura.");
                return "redirect:/citas";
            }

            Cita cita = citaExistente.get();
            cita.setUsuario(usuarioService.findById(usuarioId).orElse(null));
            cita.setProfesional(profesionalService.findById(profesionalId).orElse(null));
            cita.setServicio(servicioService.findById(servicioId).orElse(null));
            cita.setFechaHora(fecha);

            citaService.save(cita);
            redirectAttributes.addFlashAttribute("success", "Cita actualizada correctamente.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar la cita: " + e.getMessage());
        }

        return "redirect:/citas";
    }

    // ✅ ELIMINAR CITA
    @PostMapping("/eliminar")
    public String eliminarCita(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Cita> cita = citaService.findById(id);
            if (!cita.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "La cita no existe.");
                return "redirect:/citas";
            }
            
            citaService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Cita eliminada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la cita: " + e.getMessage());
        }
        return "redirect:/citas";
    }

    // ✅ CAMBIAR ESTADO - ACEPTAR
    @PostMapping("/aceptar")
    public String aceptarCita(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        return cambiarEstadoCita(id, "aceptada", redirectAttributes);
    }

    // ✅ CAMBIAR ESTADO - RECHAZAR
    @PostMapping("/rechazar")
    public String rechazarCita(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        return cambiarEstadoCita(id, "rechazada", redirectAttributes);
    }

    // ✅ CAMBIAR ESTADO - COMPLETAR
    @PostMapping("/completar")
    public String completarCita(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        return cambiarEstadoCita(id, "completada", redirectAttributes);
    }

    // ✅ MÉTODO PRIVADO PARA CAMBIAR ESTADO
    private String cambiarEstadoCita(Integer id, String estado, RedirectAttributes redirectAttributes) {
        try {
            Optional<Cita> cita = citaService.findById(id);
            if (!cita.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "La cita no existe.");
                return "redirect:/citas";
            }

            Cita citaActualizada = cita.get();
            citaActualizada.setEstado(estado);
            citaService.save(citaActualizada);

            redirectAttributes.addFlashAttribute("success", "Cita " + estado + " correctamente.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cambiar el estado: " + e.getMessage());
        }

        return "redirect:/citas";
    }

    // ✅ HISTORIAL
    @GetMapping("/historial")
    public String verHistorial(Model model) {
        model.addAttribute("citas", citaService.findAll());
        return "historial";
    }
}