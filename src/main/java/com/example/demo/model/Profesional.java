package com.example.demo.model;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "profesional")
public class Profesional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String especialidad;
    private String horarioDisponible;
    
    // CAMBIO AQUÍ: Agregar cascade y orphanRemoval
    @OneToMany(mappedBy = "profesional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cita> citas;
    
    // Getters y setters (mantén todo lo demás igual)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getEspecialidad() {
        return especialidad;
    }
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    public String getHorarioDisponible() {
        return horarioDisponible;
    }
    public void setHorarioDisponible(String horarioDisponible) {
        this.horarioDisponible = horarioDisponible;
    }
    public List<Cita> getCitas() {
        return citas;
    }
    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }
    @Override
    public String toString() {
        return "Profesional [id=" + id + ", especialidad=" + especialidad + ", horarioDisponible=" + horarioDisponible + "]";
    }
    public Profesional(Integer id, String especialidad, String horarioDisponible, List<Cita> citas) {
        super();
        this.id = id;
        this.especialidad = especialidad;
        this.horarioDisponible = horarioDisponible;
        this.citas = citas;
    }
    public Profesional() {
        super();
    }
	public Servicio getUsuario() {
		// TODO Auto-generated method stub
		return null;
	}
}