package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Profesional;
import com.example.demo.repository.ProfesionalRepository;
import java.util.List;

@Service
public class ProfesionalServiceImpl implements ProfesionalService {

    @Autowired
    private ProfesionalRepository profesionalRepository;

    @Override
    public List<Profesional> obtenerTodosLosProfesionales() {
        return profesionalRepository.findAll();
    }

    @Override
    public Profesional obtenerProfesionalPorId(Integer id) {
        return profesionalRepository.findById(id).orElse(null);
    }

    @Override
    public Profesional guardarProfesional(Profesional profesional) {
        return profesionalRepository.save(profesional);
    }

    @Override
    public void eliminarProfesional(Integer id) {
        profesionalRepository.deleteById(id);
    }

	@Override
	public Profesional obtenerServicioPorId(Integer profesionalId) {
		// TODO Auto-generated method stub
		return null;
	}
}
