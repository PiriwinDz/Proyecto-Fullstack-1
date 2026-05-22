package com.powerapp.resenas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.powerapp.resenas.dto.ResenaRequestDTO;
import com.powerapp.resenas.dto.ResenaResponseDTO;
import com.powerapp.resenas.model.Resena;
import com.powerapp.resenas.repository.ResenaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ResenaService {

    private final ResenaRepository resenaRepository;

    // GET: listar todas las reseñas
    public List<Resena> listar() {

        return resenaRepository.findAll();
    }

    // GET: buscar reseña por ID
    public Optional<Resena> buscarPorId(Long id) {

        return resenaRepository.findById(id);
    }

    // GET: buscar reseñas por usuario
    public List<Resena> buscarPorUsuario(Long usuarioId) {

        return resenaRepository.findByUsuarioId(usuarioId);
    }

    // GET: buscar reseñas por ejercicio
    public List<Resena> buscarPorEjercicio(Long ejercicioId) {

        return resenaRepository.findByEjercicioId(ejercicioId);
    }

    // GET: buscar reseñas por calificacion
    public List<Resena> buscarPorCalificacion(Integer calificacion) {

        return resenaRepository.findByCalificacion(calificacion);
    }

    // GET: buscar por comentario
    public List<Resena> buscarPorComentario(String comentario) {

        return resenaRepository
                .findByComentarioContainingIgnoreCase(comentario);
    }

    // POST: guardar reseña
    public Resena guardar(Resena resena) {

        return resenaRepository.save(resena);
    }

    // POST: crear reseña desde DTO
    public ResenaResponseDTO crearResena(ResenaRequestDTO dto) {

        Resena resena = new Resena();

        resena.setUsuarioId(dto.getUsuarioId());
        resena.setEjercicioId(dto.getEjercicioId());
        resena.setCalificacion(dto.getCalificacion());
        resena.setComentario(dto.getComentario());

        Resena resenaGuardada = resenaRepository.save(resena);

        ResenaResponseDTO response = new ResenaResponseDTO();

        response.setId(resenaGuardada.getId());
        response.setMensaje("Reseña creada correctamente");

        return response;
    }

    // DELETE: eliminar reseña
    public void eliminar(Long id) {

        resenaRepository.deleteById(id);
    }

    // PUT: actualizar reseña
    public Resena actualizar(Long id, Resena nuevaResena) {

        Resena resena = resenaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reseña no encontrada"));

        resena.setCalificacion(nuevaResena.getCalificacion());
        resena.setComentario(nuevaResena.getComentario());

        return resenaRepository.save(resena);
    }

}