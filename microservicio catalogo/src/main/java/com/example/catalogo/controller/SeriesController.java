package com.example.catalogo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.catalogo.dto.SeriesDTO;
import com.example.catalogo.model.Series;
import com.example.catalogo.service.SeriesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/series")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Gestión de Series",
    description = "Operaciones CRUD para administrar las series de ejercicios."
)
public class SeriesController {

    private final SeriesService seriesService;

    @Operation(
        summary = "Registrar serie",
        description = "Permite registrar una nueva serie para un ejercicio."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Serie registrada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Series> crear(@Valid @RequestBody SeriesDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(seriesService.crear(dto));
    }

    @Operation(
        summary = "Listar todas las series",
        description = "Obtiene todas las series registradas."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<Series>> listar() {

        return ResponseEntity.ok(seriesService.listar());
    }

    @Operation(
        summary = "Buscar serie por ID",
        description = "Obtiene una serie mediante su identificador."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Serie encontrada"),
        @ApiResponse(responseCode = "404", description = "Serie no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Series> buscarPorId(
            @PathVariable
            @Positive(message = "El ID debe ser mayor que cero")
            Long id) {

        return ResponseEntity.ok(seriesService.buscarPorId(id));
    }

    @Operation(
        summary = "Historial por ejercicio",
        description = "Obtiene todas las series registradas para un ejercicio."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Historial obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "Ejercicio no encontrado")
    })
    @GetMapping("/ejercicio/{id}")
    public ResponseEntity<List<Series>> historialPorEjercicio(
            @PathVariable
            @Positive(message = "El ID debe ser mayor que cero")
            Long id) {

        return ResponseEntity.ok(seriesService.obtenerHistorialPorEjercicio(id));
    }

    @Operation(
        summary = "Actualizar serie",
        description = "Actualiza los datos de una serie existente."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Serie actualizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Serie no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Series> actualizar(
            @PathVariable
            @Positive(message = "El ID debe ser mayor que cero")
            Long id,
            @Valid @RequestBody SeriesDTO dto) {

        return ResponseEntity.ok(seriesService.actualizar(id, dto));
    }

    @Operation(
        summary = "Eliminar serie",
        description = "Elimina una serie registrada."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Serie eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Serie no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable
            @Positive(message = "El ID debe ser mayor que cero")
            Long id) {

        seriesService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}