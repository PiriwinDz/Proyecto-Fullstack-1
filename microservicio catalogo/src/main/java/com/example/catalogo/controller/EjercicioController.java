package com.example.catalogo.controller;


import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.catalogo.dto.EjercicioDTO;
import com.example.catalogo.model.Ejercicio;
import com.example.catalogo.service.EjercicioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ejercicios")

public class EjercicioController {
    private final EjercicioService ejercicioService;

    public EjercicioController(EjercicioService ejercicioService) {
        this.ejercicioService = ejercicioService;
    }

    @PostMapping
    public ResponseEntity<Ejercicio> crear(@Valid @RequestBody EjercicioDTO dto) {
        //  el código 201 CREATED
        Ejercicio nuevoEjercicio = ejercicioService.guardarEjercicio(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEjercicio);
    }

    @GetMapping
    public ResponseEntity<List<Ejercicio>> listarTodo() {
        List<Ejercicio> ejercicios = ejercicioService.listarTodo();
        
        // 204 NO CONTENT si esta vacio
        if (ejercicios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        // Código 200 OK  
        return ResponseEntity.ok(ejercicios);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Ejercicio> buscarPorId(@PathVariable Long id) {
        // Manejo de 404 para recursos inexistentes
        return ejercicioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
