package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Profesional;
import com.example.demo.service.ProfesionalService;

import java.util.List;

@Controller
@RequestMapping("/profesionales")
public class ProfesionalController {

    @Autowired
    private ProfesionalService profesionalService;
    @GetMapping("")
    public String listarProfesionales(Model model) {
        List<Profesional> profesionales = profesionalService.obtenerTodosLosProfesionales();
        model.addAttribute("profesionales", profesionales);
        return "profesionales/Profesionaleslista"; 
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("profesional", new Profesional());
        return "profesionales/Profesionalesformulario"; 
    }

    @PostMapping("/guardar")
    public String guardarProfesional(@ModelAttribute Profesional profesional) {
        profesionalService.guardarProfesional(profesional);
        return "redirect:/profesionales";
    }
    @GetMapping("/editar/{id}")
    public String editarProfesional(@PathVariable Integer id, Model model) {
        Profesional profesional = profesionalService.obtenerProfesionalPorId(id);
        if (profesional == null) {
            return "redirect:/profesionales?error=Profesional no encontrado";
        }
        model.addAttribute("profesional", profesional);
        return "profesionales/Profesionalesformulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProfesional(@PathVariable Integer id) {
        profesionalService.eliminarProfesional(id);
        return "redirect:/profesionales";
    }
}
