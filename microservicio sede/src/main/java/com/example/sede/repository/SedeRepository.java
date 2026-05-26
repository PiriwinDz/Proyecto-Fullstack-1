package com.example.sede.repository;

import com.example.sede.model.Sede;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



public interface SedeRepository extends JpaRepository<Sede, Long> {

    
    
    List<Sede> findByActivoTrue();

    
    
    List<Sede> findByNombreContainingIgnoreCase(String nombre);
}
