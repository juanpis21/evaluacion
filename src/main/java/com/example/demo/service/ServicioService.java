package com.example.demo.service;

import com.example.demo.model.Servicio;
import java.util.List;

public interface ServicioService {
    List<Servicio> obtenerTodosLosServicios();
    Servicio obtenerServicioPorId(Integer id);
    void guardarServicio(Servicio servicio);
    void eliminarServicio(Integer id);
	Object findAll();
	void save(Servicio servicio);
	void delete(Integer id);
}
