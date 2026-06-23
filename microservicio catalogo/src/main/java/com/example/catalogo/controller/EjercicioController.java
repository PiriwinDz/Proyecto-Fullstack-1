package com.example.catalogo.controller;


import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.catalogo.dto.EjercicioDTO;
import com.example.catalogo.model.Ejercicio;
import com.example.catalogo.service.EjercicioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ejercicios")
@Tag(name = "Gestión de Ejercicios", description = "Operaciones para crear y consultar ejercicios en el catálogo.")
public class EjercicioController {
    private final EjercicioService ejercicioService;

    public EjercicioController(EjercicioService ejercicioService) {
        this.ejercicioService = ejercicioService;
    }

    @Operation(summary = "Crear un nuevo ejercicio", description = "Crea un nuevo ejercicio en el catálogo con los datos proporcionados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ejercicio creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<Ejercicio> crear(@Valid @RequestBody EjercicioDTO dto) {
       
        Ejercicio nuevoEjercicio = ejercicioService.guardarEjercicio(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEjercicio);
    }

    @Operation(summary = "Listar todos los ejercicios", description = "Devuelve una lista de todos los ejercicios disponibles en el catálogo.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de ejercicios obtenida"),
        @ApiResponse(responseCode = "204", description = "No hay ejercicios en el catálogo")
    })
    @GetMapping
    public ResponseEntity<List<Ejercicio>> listarTodo() {
        List<Ejercicio> ejercicios = ejercicioService.listarTodo();
        
        
        if (ejercicios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        
        return ResponseEntity.ok(ejercicios);
    }
    
    @Operation(summary = "Buscar un ejercicio por ID", description = "Devuelve un único ejercicio basado en su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ejercicio encontrado"),
        @ApiResponse(responseCode = "404", description = "Ejercicio no encontrado para el ID proporcionado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Ejercicio> buscarPorId(
        @Parameter(description = "ID del ejercicio a buscar.", required = true)
        @PathVariable Long id) {
        
        return ejercicioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
