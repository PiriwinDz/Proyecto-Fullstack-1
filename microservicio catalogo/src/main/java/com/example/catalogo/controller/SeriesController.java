package com.example.catalogo.controller;


import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.catalogo.dto.SeriesDTO;
import com.example.catalogo.model.Series;
import com.example.catalogo.service.SeriesService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/series") 
public class SeriesController {
    private final SeriesService seriesService; //

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @PostMapping
    public ResponseEntity<Series> registrar(@Valid @RequestBody SeriesDTO dto) {
    //  el código 201 CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(seriesService.registrarSerie(dto));
    }

    @GetMapping("/ejercicio/{id}")
    public ResponseEntity<List<Series>> historial(@PathVariable Long id) {
        List<Series> series = seriesService.obtenerHistorialPorEjercicio(id);
        
        //  204 NO CONTENT si la lista está vacía
        if (series.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        // 200 OK
        return ResponseEntity.ok(series);
    }
}
