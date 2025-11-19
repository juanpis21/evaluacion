package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.demo.model.Cita;
import com.example.demo.repository.CitaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Override
    public List<Cita> obtenerCitasPorUsuario(Integer usuarioId) {
        System.out.println("=== DEBUG: Buscando citas para usuario ID: " + usuarioId);
        List<Cita> citas = citaRepository.findByUsuarioId(usuarioId);
        System.out.println("=== DEBUG: Citas encontradas: " + citas.size());
        for (Cita cita : citas) {
            System.out.println(
                "Cita ID: " + cita.getId() +
                ", Estado: " + cita.getEstado() +
                ", Servicio: " + (cita.getServicio() != null ? cita.getServicio().getNombre() : "null") +
                ", Profesional: " + (cita.getProfesional() != null ? cita.getProfesional().getNombre() : "null")
            );
        }
        return citas;
    }

    @Override
    public List<Cita> obtenerTodasLasCitas() {
        return citaRepository.findAll();
    }

    @Override
    public Cita guardarCita(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    public void eliminarCita(Integer id) {
        citaRepository.deleteById(id);
    }

    @Override
    public List<Cita> findAll() {
        return citaRepository.findAll();
    }

    @Override
    public Optional<Cita> findById(Integer id) {
        return citaRepository.findById(id);
    }

    @Override
    public Cita save(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    public void delete(Integer id) {
        citaRepository.deleteById(id);
    }

    // Métodos adicionales para búsquedas específicas
    
    @Override
    public List<Cita> findByEstado(String estado) {
        return citaRepository.findByEstado(estado);
    }
    
    @Override
    public List<Cita> findByProfesionalId(Integer profesionalId) {
        return citaRepository.findByProfesionalId(profesionalId);
    }
    
    @Override
    public List<Cita> findByServicioId(Integer servicioId) {
        return citaRepository.findByServicioId(servicioId);
    }
    
    @Override
    public List<Cita> findByFechaHoraBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return citaRepository.findByFechaHoraBetween(startDate, endDate);
    }
    
    @Override
    public List<Cita> findByUsuarioIdAndEstado(Integer usuarioId, String estado) {
        return citaRepository.findByUsuarioIdAndEstado(usuarioId, estado);
    }
    
    @Override
    public List<Cita> findCitasProximas(LocalDateTime fechaInicio) {
        LocalDateTime fechaFin = fechaInicio.plusDays(7); // Próximos 7 días
        return citaRepository.findByFechaHoraBetween(fechaInicio, fechaFin);
    }
    
    @Override
    public List<Cita> findCitasPorFecha(LocalDateTime fecha) {
        LocalDateTime inicioDia = fecha.toLocalDate().atStartOfDay();
        LocalDateTime finDia = fecha.toLocalDate().atTime(23, 59, 59);
        return citaRepository.findByFechaHoraBetween(inicioDia, finDia);
    }
    
    @Override
    public boolean existeCitaEnMismoHorario(Integer profesionalId, LocalDateTime fechaHora) {
        LocalDateTime inicio = fechaHora.minusMinutes(59); // 1 hora antes
        LocalDateTime fin = fechaHora.plusMinutes(59); // 1 hora después
        
        List<Cita> citasSolapadas = citaRepository.findByProfesionalId(profesionalId)
            .stream()
            .filter(cita -> 
                cita.getFechaHora().isAfter(inicio) && 
                cita.getFechaHora().isBefore(fin) &&
                !cita.getEstado().equals("cancelada")
            )
            .toList();
        
        return !citasSolapadas.isEmpty();
    }
    
    @Override
    public long contarCitasPorEstado(String estado) {
        return citaRepository.findByEstado(estado).size();
    }
    
    @Override
    public List<Cita> findCitasVencidas() {
        LocalDateTime ahora = LocalDateTime.now();
        return citaRepository.findAll()
            .stream()
            .filter(cita -> 
                cita.getFechaHora().isBefore(ahora) && 
                "pendiente".equals(cita.getEstado())
            )
            .toList();
    }

	@Override
	public List<Cita> findHistorial() {
		// TODO Auto-generated method stub
		return null;
	}
}