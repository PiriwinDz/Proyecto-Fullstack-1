package com.powerapp.resenas.repository;

import com.powerapp.resenas.model.Resena;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ResenaRepository extends JpaRepository<Resena, Long> {

    // Buscar reseñas por usuario
    List<Resena> findByUsuarioId(Long usuarioId);

    // Buscar reseñas por ejercicio
    List<Resena> findByEjercicioId(Long ejercicioId);

    // Buscar reseñas por calificacion
    List<Resena> findByCalificacion(Integer calificacion);

    // Buscar reseñas que contengan texto en comentario
    List<Resena> findByComentarioContainingIgnoreCase(String comentario);

}
