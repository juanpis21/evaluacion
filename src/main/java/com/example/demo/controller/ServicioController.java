package com.example.demo.controller;

import com.example.demo.model.Servicio;
import com.example.demo.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    // Mostrar todos los servicios
    @GetMapping
    public String listarServicios(Model model) {
        model.addAttribute("servicios", servicioService.obtenerTodosLosServicios());
        return "servicios"; // nombre del HTML Thymeleaf
    }

    // Guardar o actualizar un servicio
    @PostMapping("/guardar")
    public String guardarServicio(@ModelAttribute Servicio servicio, RedirectAttributes redirectAttributes) {
        try {
            servicioService.guardarServicio(servicio);
            redirectAttributes.addFlashAttribute("success", "Servicio guardado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el servicio.");
        }
        return "redirect:/servicios";
    }

    // Eliminar un servicio
    @GetMapping("/eliminar/{id}")
    public String eliminarServicio(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            servicioService.eliminarServicio(id);
            redirectAttributes.addFlashAttribute("success", "Servicio eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el servicio.");
        }
        return "redirect:/servicios";
    }
}
