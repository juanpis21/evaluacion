package com.example.demo.controller;

import com.example.demo.model.Cita;
import com.example.demo.model.Usuario;
import com.example.demo.model.Servicio;
import com.example.demo.service.CitaService;
import com.example.demo.service.UsuarioService;
import com.example.demo.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private ServicioService servicioService;

    // 📄 Mostrar todas las citas en la misma página con modal
    @GetMapping
    public String listarCitas(Model model) {
        List<Cita> citas = citaService.findAll();
        List<Usuario> usuarios = usuarioService.findAll();
        List<Servicio> servicios = servicioService.findAll();

        model.addAttribute("citas", citas);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("servicios", servicios);
        model.addAttribute("cita", new Cita()); // objeto para el modal de nueva cita

        return "citas"; // plantilla unificada con modales
    }

    // 💾 Guardar o actualizar cita
    @PostMapping("/guardar")
    public String guardarCita(@ModelAttribute("cita") Cita cita,
                              @RequestParam("usuarioId") Integer usuarioId,
                              @RequestParam("servicioId") Integer servicioId,
                              RedirectAttributes redirect) {

        Optional<Usuario> usuarioOpt = usuarioService.get(usuarioId);
        Optional<Servicio> servicioOpt = servicioService.get(servicioId);

        if (usuarioOpt.isEmpty() || servicioOpt.isEmpty()) {
            redirect.addFlashAttribute("error", "Debe seleccionar un cliente y un servicio válidos.");
            return "redirect:/citas";
        }

        cita.setUsuario(usuarioOpt.get());
        cita.setServicio(servicioOpt.get());

        citaService.save(cita);
        redirect.addFlashAttribute("success", "Cita guardada correctamente.");
        return "redirect:/citas";
    }

    // 🗑️ Eliminar cita
    @GetMapping("/eliminar/{id}")
    public String eliminarCita(@PathVariable Integer id, RedirectAttributes redirect) {
        citaService.delete(id);
        redirect.addFlashAttribute("success", "Cita eliminada correctamente.");
        return "redirect:/citas";
    }
}
