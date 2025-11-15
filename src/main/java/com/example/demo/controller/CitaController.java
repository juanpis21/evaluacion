package com.example.demo.controller;

import com.example.demo.model.Cita;
import com.example.demo.model.Usuario;
import com.example.demo.model.Profesional;
import com.example.demo.model.Servicio;
import com.example.demo.repository.CitaRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.repository.ProfesionalRepository;
import com.example.demo.repository.ServicioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CitaController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProfesionalRepository profesionalRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private CitaRepository citaRepository;
    
    @GetMapping("/citas/nueva")
    public String mostrarFormulario(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<Profesional> profesionales = profesionalRepository.findAll();
        List<Servicio> servicios = servicioRepository.findAll();

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("profesionales", profesionales);
        model.addAttribute("servicios", servicios);

        return "nueva-cita";
    }
    @PostMapping("/citas/nueva")
    public String crearCita( @RequestParam("usuarioId") Integer usuarioId,@RequestParam("profesionalId") Integer profesionalId,@RequestParam("servicioId") Integer servicioId,
            @RequestParam("fechaHora") String fechaHora,
            Model model
    ) {
        try {
            Usuario usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

            Profesional profesional = profesionalRepository.findById(profesionalId)
                    .orElseThrow(() -> new IllegalArgumentException("Profesional no encontrado"));

            Servicio servicio = servicioRepository.findById(servicioId)
                    .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));

            Cita cita = new Cita();
            cita.setUsuario(usuario);
            cita.setProfesional(profesional);
            cita.setServicio(servicio);
            cita.setFechaHora(LocalDateTime.parse(fechaHora));
            citaRepository.save(cita);
            return "redirect:/citas/historial";

        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error al agendar la cita: " + e.getMessage());
            model.addAttribute("usuarios", usuarioRepository.findAll());
            model.addAttribute("profesionales", profesionalRepository.findAll());
            model.addAttribute("servicios", servicioRepository.findAll());
            return "nueva-cita";
        }
    }
    @GetMapping("/citas/historial")
    public String mostrarHistorial(Model model) {
        List<Cita> citas = citaRepository.findAll();
        model.addAttribute("citas", citas);
        return "historial";
    }
}
