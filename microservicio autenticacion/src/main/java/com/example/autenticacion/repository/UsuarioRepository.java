package com.example.autenticacion.repository;

import com.example.autenticacion.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;



public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    
    
    Optional<Usuario> findByCorreo(String correo);

    
    
    boolean existsByCorreo(String correo);
}
