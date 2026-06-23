package com.example.catalogo.controller;


import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.catalogo.dto.SeriesDTO;
import com.example.catalogo.model.Series;
import com.example.catalogo.service.SeriesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/series")
@Tag(name = "Gestión de Series", description = "Operaciones para registrar y consultar series de ejercicios.")
public class SeriesController {
    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @Operation(summary = "Registrar una nueva serie", description = "Crea una nueva serie de ejercicio con los datos proporcionados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Serie registrada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<Series> registrar(@Valid @RequestBody SeriesDTO dto) {
    
        return ResponseEntity.status(HttpStatus.CREATED).body(seriesService.registrarSerie(dto));
    }

    @Operation(summary = "Obtener historial de series por ejercicio", description = "Devuelve una lista de todas las series registradas para un ejercicio específico, ordenadas por fecha.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Historial de series encontrado"),
        @ApiResponse(responseCode = "204", description = "No hay contenido, el historial de series para el ejercicio está vacío"),
        @ApiResponse(responseCode = "404", description = "Ejercicio no encontrado")
    })
    @GetMapping("/ejercicio/{id}")
    public ResponseEntity<List<Series>> historial(
        @Parameter(description = "ID del ejercicio para obtener el historial.", required = true)
        @PathVariable Long id) {
        List<Series> series = seriesService.obtenerHistorialPorEjercicio(id);
        
        
        if (series.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        
        return ResponseEntity.ok(series);
    }
}
