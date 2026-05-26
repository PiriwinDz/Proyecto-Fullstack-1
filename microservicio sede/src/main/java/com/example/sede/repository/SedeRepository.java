package com.example.sede.repository;

import com.example.sede.model.Sede;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository provee automaticamente: save, findById, findAll, delete, count, etc.
// el primer parametro es la entidad (Sede), el segundo es el tipo del id (Long)
public interface SedeRepository extends JpaRepository<Sede, Long> {

    // Spring genera el SQL automaticamente por el nombre del metodo
    // SELECT * FROM sedes WHERE activo = true
    List<Sede> findByActivoTrue();

    // SELECT * FROM sedes WHERE nombre LIKE %nombre%
    // busca sedes cuyo nombre contenga el texto ingresado (sin distinguir mayusculas)
    List<Sede> findByNombreContainingIgnoreCase(String nombre);
}
