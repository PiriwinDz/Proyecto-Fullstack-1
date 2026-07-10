package com.example.sede.controller;

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

import com.example.sede.dto.SedeRequestDTO;
import com.example.sede.dto.SedeResponseDTO;
import com.example.sede.service.SedeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/api/sedes")
@RequiredArgsConstructor
@Tag(
    name = "Sedes",
    description = "API para la gestión de sedes del gimnasio"
)
public class SedeController {

    private final SedeService sedeService;

    @Operation(
        summary = "Listar sedes",
        description = "Obtiene todas las sedes registradas en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de sedes obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<SedeResponseDTO>> listar() {
        return ResponseEntity.ok(sedeService.listar());
    }

    @Operation(
        summary = "Buscar sede por ID",
        description = "Obtiene la información de una sede mediante su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sede encontrada"),
        @ApiResponse(responseCode = "400", description = "Identificador inválido"),
        @ApiResponse(responseCode = "404", description = "Sede no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SedeResponseDTO> buscarPorId(
            @PathVariable @Positive(message = "El ID debe ser mayor que cero") Long id) {

        return ResponseEntity.ok(sedeService.buscarPorId(id));
    }

    @Operation(
        summary = "Crear sede",
        description = "Registra una nueva sede en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Sede creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<SedeResponseDTO> crear(
            @Valid @RequestBody SedeRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sedeService.crear(dto));
    }

    @Operation(
        summary = "Actualizar sede",
        description = "Actualiza los datos de una sede existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sede actualizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Sede no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<SedeResponseDTO> actualizar(
            @PathVariable @Positive(message = "El ID debe ser mayor que cero") Long id,
            @Valid @RequestBody SedeRequestDTO dto) {

        return ResponseEntity.ok(sedeService.actualizar(id, dto));
    }

    @Operation(
        summary = "Registrar entrada",
        description = "Incrementa en uno la ocupación actual de una sede"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entrada registrada correctamente"),
        @ApiResponse(responseCode = "400", description = "La sede alcanzó su capacidad máxima"),
        @ApiResponse(responseCode = "404", description = "Sede no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}/entrada")
    public ResponseEntity<SedeResponseDTO> registrarEntrada(
            @PathVariable @Positive(message = "El ID debe ser mayor que cero") Long id) {

        return ResponseEntity.ok(sedeService.registrarEntrada(id));
    }

    @Operation(
        summary = "Registrar salida",
        description = "Disminuye en uno la ocupación actual de una sede"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Salida registrada correctamente"),
        @ApiResponse(responseCode = "400", description = "La ocupación ya es cero"),
        @ApiResponse(responseCode = "404", description = "Sede no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}/salida")
    public ResponseEntity<SedeResponseDTO> registrarSalida(
            @PathVariable @Positive(message = "El ID debe ser mayor que cero") Long id) {

        return ResponseEntity.ok(sedeService.registrarSalida(id));
    }

    @Operation(
        summary = "Eliminar sede",
        description = "Elimina una sede del sistema mediante su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Sede eliminada correctamente"),
        @ApiResponse(responseCode = "400", description = "Identificador inválido"),
        @ApiResponse(responseCode = "404", description = "Sede no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable @Positive(message = "El ID debe ser mayor que cero") Long id) {

        sedeService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}