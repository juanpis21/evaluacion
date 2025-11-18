package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Profesional;

@Repository
public interface ProfesionalRepository extends JpaRepository<Profesional, Integer> {
}
