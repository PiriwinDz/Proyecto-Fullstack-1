package com.example.catalogo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.catalogo.dto.EjercicioDTO;
import com.example.catalogo.exception.EjercicioNoEncontradoException;
import com.example.catalogo.model.Ejercicio;
import com.example.catalogo.repository.EjercicioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EjercicioService {

    private final EjercicioRepository ejercicioRepository;

    public Ejercicio crear(EjercicioDTO dto) {

        Ejercicio ejercicio = new Ejercicio();

        ejercicio.setNombre(dto.getNombre());
        ejercicio.setGrupoMuscular(dto.getGrupoMuscular());
        ejercicio.setDescripcion(dto.getDescripcion());

        return ejercicioRepository.save(ejercicio);
    }

    public List<Ejercicio> listar() {
        return ejercicioRepository.findAll();
    }

    public Ejercicio buscarPorId(Long id) {
        return obtenerEjercicio(id);
    }

    public Ejercicio actualizar(Long id, EjercicioDTO dto) {

        Ejercicio ejercicio = obtenerEjercicio(id);

        ejercicio.setNombre(dto.getNombre());
        ejercicio.setGrupoMuscular(dto.getGrupoMuscular());
        ejercicio.setDescripcion(dto.getDescripcion());

        return ejercicioRepository.save(ejercicio);
    }

    public void eliminar(Long id) {

        Ejercicio ejercicio = obtenerEjercicio(id);

        ejercicioRepository.delete(ejercicio);
    }

    private Ejercicio obtenerEjercicio(Long id) {

        return ejercicioRepository.findById(id)
                .orElseThrow(() ->
                        new EjercicioNoEncontradoException(id));
    }

}