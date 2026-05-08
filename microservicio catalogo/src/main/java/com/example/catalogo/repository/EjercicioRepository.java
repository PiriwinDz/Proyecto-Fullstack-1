package com.example.catalogo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.catalogo.model.Ejercicio;

@Repository
public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {
    Optional<Ejercicio>findByNombre(String nombre);
    List<Ejercicio>findByGrupoMuscular(String GrupoMuscular);

}
