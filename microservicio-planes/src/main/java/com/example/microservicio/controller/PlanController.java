package com.example.microservicio.controller;

import com.example.microservicio.dto.PlanRequestDTO;
import com.example.microservicio.dto.PlanResponseDTO;
import com.example.microservicio.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
@RequiredArgsConstructor
@Tag(name = "Gestión de Planes", description = "Operaciones CRUD para la gestión de planes de entrenamiento.")
public class PlanController {

    private final PlanService planService;

    @Operation(summary = "Crear un nuevo plan", description = "Crea un nuevo plan de entrenamiento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plan creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public ResponseEntity<PlanResponseDTO> createPlan(@Valid @RequestBody PlanRequestDTO requestDTO) {
        PlanResponseDTO response = planService.createPlan(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener todos los planes", description = "Devuelve una lista de todos los planes de entrenamiento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Planes encontrados"),
            @ApiResponse(responseCode = "204", description = "No hay planes disponibles")
    })
    @GetMapping
    public ResponseEntity<List<PlanResponseDTO>> getAllPlanes() {
        List<PlanResponseDTO> responses = planService.getAllPlanes();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Obtener un plan por ID", description = "Devuelve un plan de entrenamiento específico basado en su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plan encontrado"),
            @ApiResponse(responseCode = "404", description = "Plan no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PlanResponseDTO> getPlanById(
            @Parameter(description = "ID del plan a buscar.", required = true)
            @PathVariable Long id) {
        PlanResponseDTO response = planService.getPlanById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Actualizar un plan", description = "Actualiza un plan de entrenamiento existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plan actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Plan no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PlanResponseDTO> updatePlan(
            @Parameter(description = "ID del plan a actualizar.", required = true)
            @PathVariable Long id,
            @Valid @RequestBody PlanRequestDTO requestDTO) {
        PlanResponseDTO response = planService.updatePlan(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar un plan", description = "Elimina un plan de entrenamiento por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Plan eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Plan no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(
            @Parameter(description = "ID del plan a eliminar.", required = true)
            @PathVariable Long id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }
}
