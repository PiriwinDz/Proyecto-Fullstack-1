package com.powerapp.resenas.controller;

import com.powerapp.resenas.dto.ResenaRequestDTO;
import com.powerapp.resenas.dto.ResenaResponseDTO;
import com.powerapp.resenas.model.Resena;
import com.powerapp.resenas.service.ResenaService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/resenas")
@RequiredArgsConstructor
@Validated

public class ResenaController {

    private final ResenaService resenaService;

    // GET: listar todas las reseñas
    @GetMapping
    public List<Resena> listar() {

        return resenaService.listar();
    }

    // GET: buscar reseña por ID
    @GetMapping("/{id}")
    public Optional<Resena> buscarPorId(@PathVariable Long id) {

        return resenaService.buscarPorId(id);
    }

    // GET: buscar reseñas por usuario
    @GetMapping("/usuario/{usuarioId}")
    public List<Resena> buscarPorUsuario(@PathVariable Long usuarioId) {

        return resenaService.buscarPorUsuario(usuarioId);
    }

    // GET: buscar reseñas por ejercicio
    @GetMapping("/ejercicio/{ejercicioId}")
    public List<Resena> buscarPorEjercicio(
            @PathVariable Long ejercicioId) {

        return resenaService.buscarPorEjercicio(ejercicioId);
    }

    // GET: buscar por calificacion
    @GetMapping("/calificacion/{calificacion}")
    public List<Resena> buscarPorCalificacion(
            @PathVariable Integer calificacion) {

        return resenaService.buscarPorCalificacion(calificacion);
    }

    // GET: buscar por comentario
    @GetMapping("/comentario/{comentario}")
    public List<Resena> buscarPorComentario(
            @PathVariable String comentario) {

        return resenaService.buscarPorComentario(comentario);
    }

    // POST: crear reseña
    @PostMapping
    public ResponseEntity<ResenaResponseDTO> crearResena(
            @Valid @RequestBody ResenaRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resenaService.crearResena(dto));
    }

    // PUT: actualizar reseña
    @PutMapping("/{id}")
    public ResponseEntity<Resena> actualizar(
            @PathVariable Long id,
            @RequestBody Resena resena) {

        Resena resenaActualizada =
                resenaService.actualizar(id, resena);

        return ResponseEntity.ok(resenaActualizada);
    }

    // DELETE: eliminar reseña
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @PathVariable Long id) {

        resenaService.eliminar(id);

        return ResponseEntity
                .ok("Reseña eliminada correctamente");
    }

}
