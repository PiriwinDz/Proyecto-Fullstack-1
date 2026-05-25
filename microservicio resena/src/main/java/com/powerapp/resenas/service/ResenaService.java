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

    public List<Resena> listar() {

        return resenaRepository.findAll();
    }

    public Optional<Resena> buscarPorId(Long id) {

        return resenaRepository.findById(id);
    }

    public List<Resena> buscarPorUsuario(Long usuarioId) {

        return resenaRepository.findByUsuarioId(usuarioId);
    }

    public List<Resena> buscarPorEjercicio(Long ejercicioId) {

        return resenaRepository.findByEjercicioId(ejercicioId);
    }

    public List<Resena> buscarPorCalificacion(Integer calificacion) {

        return resenaRepository.findByCalificacion(calificacion);
    }

    public List<Resena> buscarPorComentario(String comentario) {

        return resenaRepository
                .findByComentarioContainingIgnoreCase(comentario);
    }

    public Resena guardar(Resena resena) {

        return resenaRepository.save(resena);
    }

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

    public void eliminar(Long id) {

        resenaRepository.deleteById(id);
    }

    public Resena actualizar(Long id, Resena nuevaResena) {

        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada"));

        resena.setCalificacion(nuevaResena.getCalificacion());
        resena.setComentario(nuevaResena.getComentario());

        return resenaRepository.save(resena);
    }

}