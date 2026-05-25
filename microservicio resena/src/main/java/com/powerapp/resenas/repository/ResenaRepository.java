package com.powerapp.resenas.repository;

import com.powerapp.resenas.model.Resena;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ResenaRepository extends JpaRepository<Resena, Long> {

    List<Resena> findByUsuarioId(Long usuarioId);

    List<Resena> findByEjercicioId(Long ejercicioId);

    List<Resena> findByCalificacion(Integer calificacion);

    List<Resena> findByComentarioContainingIgnoreCase(String comentario);

}
