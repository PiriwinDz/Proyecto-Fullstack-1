package com.example.catalogo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.catalogo.dto.EjercicioDTO;
import com.example.catalogo.model.Ejercicio;
import com.example.catalogo.repository.EjercicioRepository;

@Service

public class EjercicioService {
    private final EjercicioRepository ejercicioRepository;

    
    public EjercicioService(EjercicioRepository ejercicioRepository) {
        this.ejercicioRepository = ejercicioRepository;
    }

    public Ejercicio guardarEjercicio(EjercicioDTO dto){

        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setNombre(dto.getNombre());
        ejercicio.setGrupoMuscular(dto.getGrupoMuscular());
        ejercicio.setDescripcion(dto.getDescripcion());

        return ejercicioRepository.save(ejercicio);
    }

    public List<Ejercicio> listarTodo() {
        return ejercicioRepository.findAll();
    }

    public Optional<Ejercicio> buscarPorId(Long id) {
        return ejercicioRepository.findById(id);
    }
}
