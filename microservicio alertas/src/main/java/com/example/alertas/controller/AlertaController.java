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
        description = "Obtiene todas las alertas activas registradas en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<AlertaResponseDTO>> listar() {
        return ResponseEntity.ok(alertaService.listar());
    }

    @Operation(
        summary = "Buscar alerta por ID",
        description = "Obtiene la información detallada de una alerta mediante su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Alerta encontrada"),
        @ApiResponse(responseCode = "404", description = "Alerta no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AlertaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(alertaService.buscarPorId(id));
    }

    @Operation(
        summary = "Crear alerta",
        description = "Permite registrar una nueva alerta para ser gestionada por el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Alerta creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<AlertaResponseDTO> crear(
            @Valid @RequestBody AlertaRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(alertaService.crear(dto));
    }

    @Operation(
        summary = "Desactivar alerta",
        description = "Permite desactivar una alerta existente mediante su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Alerta desactivada correctamente"),
        @ApiResponse(responseCode = "404", description = "Alerta no encontrada"),
        @ApiResponse(responseCode = "400", description = "La alerta ya se encuentra desactivada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<AlertaResponseDTO> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(alertaService.desactivar(id));
    }
}