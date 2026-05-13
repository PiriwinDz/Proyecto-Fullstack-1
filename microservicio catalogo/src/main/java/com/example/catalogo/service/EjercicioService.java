package com.example.catalogo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.catalogo.dto.EjercicioDTO;
import com.example.catalogo.model.Ejercicio;
import com.example.catalogo.repository.EjercicioRepository;

@Service

public class EjercicioService {
    private final EjercicioRepository ejercicioRepository;

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
}
