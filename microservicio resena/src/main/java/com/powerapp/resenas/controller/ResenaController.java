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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/resenas")
@RequiredArgsConstructor
@Validated
public class ResenaController {

    private final ResenaService resenaService;

    @Operation(summary = "Listar todas las reseñas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    })
    @GetMapping
    public List<Resena> listar() {
        return resenaService.listar();
    }

    @Operation(summary = "Buscar reseña por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reseña encontrada"),
            @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @GetMapping("/{id}")
    public Optional<Resena> buscarPorId(@PathVariable Long id) {
        return resenaService.buscarPorId(id);
    }

    @Operation(summary = "Buscar reseñas por usuario")
    @ApiResponse(responseCode = "200", description = "Reseñas encontradas para el usuario")
    @GetMapping("/usuario/{usuarioId}")
    public List<Resena> buscarPorUsuario(@PathVariable Long usuarioId) {
        return resenaService.buscarPorUsuario(usuarioId);
    }

    @Operation(summary = "Buscar reseñas por ejercicio")
    @ApiResponse(responseCode = "200", description = "Reseñas encontradas para el ejercicio")
    @GetMapping("/ejercicio/{ejercicioId}")
    public List<Resena> buscarPorEjercicio(@PathVariable Long ejercicioId) {
        return resenaService.buscarPorEjercicio(ejercicioId);
    }

    @Operation(summary = "Buscar reseñas por calificación")
    @ApiResponse(responseCode = "200", description = "Reseñas encontradas con la calificación indicada")
    @GetMapping("/calificacion/{calificacion}")
    public List<Resena> buscarPorCalificacion(@PathVariable Integer calificacion) {
        return resenaService.buscarPorCalificacion(calificacion);
    }

    @Operation(summary = "Buscar reseñas por comentario")
    @ApiResponse(responseCode = "200", description = "Reseñas encontradas con el comentario indicado")
    @GetMapping("/comentario/{comentario}")
    public List<Resena> buscarPorComentario(@PathVariable String comentario) {
        return resenaService.buscarPorComentario(comentario);
    }

    @Operation(summary = "Crear una nueva reseña")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reseña creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud")
    })
    @PostMapping
    public ResponseEntity<ResenaResponseDTO> crearResena(
            @Valid @RequestBody ResenaRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resenaService.crearResena(dto));
    }

    @Operation(summary = "Actualizar una reseña existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reseña actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Resena> actualizar(
            @PathVariable Long id,
            @RequestBody Resena resena) {
        Resena resenaActualizada = resenaService.actualizar(id, resena);
        return ResponseEntity.ok(resenaActualizada);
    }

    @Operation(summary = "Eliminar una reseña")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reseña eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        resenaService.eliminar(id);
        return ResponseEntity.ok("Reseña eliminada correctamente");
    }
}
