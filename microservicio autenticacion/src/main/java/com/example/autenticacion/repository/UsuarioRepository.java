package com.example.autenticacion.repository;

import com.example.autenticacion.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository provee automaticamente: save, findById, findAll, delete, etc.
// el primer parametro es la entidad (Usuario), el segundo es el tipo del id (Long)
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Spring genera el SQL automaticamente segun el nombre del metodo
    // SELECT * FROM usuarios WHERE correo = ?
    Optional<Usuario> findByCorreo(String correo);

    // SELECT COUNT(*) > 0 FROM usuarios WHERE correo = ?
    // retorna true si ya existe un usuario con ese correo
    boolean existsByCorreo(String correo);
}
