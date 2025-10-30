package com.example.demo.service;

import com.example.demo.model.Servicio;
import com.example.demo.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Override
    public List<Servicio> obtenerTodosLosServicios() {
        return servicioRepository.findAll();
    }

    @Override
    public Servicio obtenerServicioPorId(Integer id) {
        return servicioRepository.findById(id).orElse(null);
    }

    @Override
    public void guardarServicio(Servicio servicio) {
        servicioRepository.save(servicio);
    }

    @Override
    public void eliminarServicio(Integer id) {
        servicioRepository.deleteById(id);
    }

	@Override
	public Object findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Servicio servicio) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}
}
