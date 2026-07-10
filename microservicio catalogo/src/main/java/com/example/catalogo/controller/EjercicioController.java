package com.example.catalogo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.catalogo.dto.EjercicioDTO;
import com.example.catalogo.model.Ejercicio;
import com.example.catalogo.service.EjercicioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ejercicios")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Gestión de Ejercicios",
    description = "Operaciones CRUD para administrar el catálogo de ejercicios."
)
public class EjercicioController {

    private final EjercicioService ejercicioService;

    @Operation(
        summary = "Crear ejercicio",
        description = "Permite registrar un nuevo ejercicio en el catálogo."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Ejercicio creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Ejercicio> crear(@Valid @RequestBody EjercicioDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ejercicioService.crear(dto));
    }

    @Operation(
        summary = "Listar ejercicios",
        description = "Obtiene todos los ejercicios registrados."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<Ejercicio>> listar() {

        return ResponseEntity.ok(ejercicioService.listar());
    }

    @Operation(
        summary = "Buscar ejercicio por ID",
        description = "Obtiene un ejercicio específico mediante su identificador."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ejercicio encontrado"),
        @ApiResponse(responseCode = "404", description = "Ejercicio no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Ejercicio> buscarPorId(
            @PathVariable
            @Positive(message = "El ID debe ser mayor que cero")
            Long id) {

        return ResponseEntity.ok(ejercicioService.buscarPorId(id));
    }

    @Operation(
        summary = "Actualizar ejercicio",
        description = "Actualiza la información de un ejercicio existente."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ejercicio actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Ejercicio no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Ejercicio> actualizar(
            @PathVariable
            @Positive(message = "El ID debe ser mayor que cero")
            Long id,
            @Valid @RequestBody EjercicioDTO dto) {

        return ResponseEntity.ok(ejercicioService.actualizar(id, dto));
    }

    @Operation(
        summary = "Eliminar ejercicio",
        description = "Elimina un ejercicio del catálogo."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Ejercicio eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Ejercicio no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable
            @Positive(message = "El ID debe ser mayor que cero")
            Long id) {

        ejercicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
