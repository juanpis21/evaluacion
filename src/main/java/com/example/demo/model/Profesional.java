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

    @OneToMany(mappedBy = "profesional")
    private List<Cita> citas;

    // Getters y setters
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

	//to string
	@Override
	public String toString() {
		return "Profesional [id=" + id + ", especialidad=" + especialidad + ", horarioDisponible=" + horarioDisponible
				+ ", citas=" + citas + "]";
	}

	//constructor using fields
	public Profesional(Integer id, String especialidad, String horarioDisponible, List<Cita> citas) {
		super();
		this.id = id;
		this.especialidad = especialidad;
		this.horarioDisponible = horarioDisponible;
		this.citas = citas;
	}

	//Super Class
	public Profesional() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Servicio getUsuario() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
    
}