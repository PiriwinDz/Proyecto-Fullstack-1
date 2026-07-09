package com.example.alertas.controller;

import com.example.alertas.dto.AlertaRequestDTO;
import com.example.alertas.dto.AlertaResponseDTO;
import com.example.alertas.service.AlertaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alertas")
@RequiredArgsConstructor
public class AlertaController {

    private final AlertaService alertaService;

    @Operation(
            summary = "Listar alertas",
            description = "Obtiene todas las alertas registradas en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<AlertaResponseDTO>> listar() {

        List<AlertaResponseDTO> alertas = alertaService.listar();

        return ResponseEntity.ok(alertas);
    }

    @Operation(
            summary = "Buscar alerta por ID",
            description = "Obtiene una alerta mediante su identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alerta encontrada"),
            @ApiResponse(responseCode = "404", description = "Alerta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AlertaResponseDTO> buscarPorId(@PathVariable Long id) {

        AlertaResponseDTO alerta = alertaService.buscarPorId(id);

        return ResponseEntity.ok(alerta);
    }

    @Operation(
            summary = "Crear alerta",
            description = "Registra una nueva alerta."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alerta creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<AlertaResponseDTO> crear(@Valid @RequestBody AlertaRequestDTO dto) {

        AlertaResponseDTO alerta = alertaService.crear(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(alerta);
    }

    @Operation(
            summary = "Actualizar alerta",
            description = "Actualiza una alerta existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alerta actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Alerta no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AlertaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody AlertaRequestDTO dto) {

        AlertaResponseDTO alerta = alertaService.actualizar(id, dto);

        return ResponseEntity.ok(alerta);
    }

    @Operation(
            summary = "Eliminar alerta",
            description = "Elimina una alerta del sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alerta eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Alerta no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        alertaService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

}