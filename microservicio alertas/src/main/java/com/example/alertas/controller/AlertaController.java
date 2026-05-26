package com.example.alertas.controller;

// dto request
import com.example.alertas.dto.AlertaRequestDTO;

// dto response
import com.example.alertas.dto.AlertaResponseDTO;

// service
import com.example.alertas.service.AlertaService;

// validaciones
import jakarta.validation.Valid;

// lombok
import lombok.RequiredArgsConstructor;

// http
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// rest
import org.springframework.web.bind.annotation.*;

// listas
import java.util.List;

// controller rest
@RestController

// ruta base
@RequestMapping("/api/v1/alertas")

// constructor automatico
@RequiredArgsConstructor
public class AlertaController {

    // service inyectado
    private final AlertaService alertaService;

    // obtiene todas las alertas
    @GetMapping
    public ResponseEntity<List<AlertaResponseDTO>> listar() {

        // retorna lista
        return ResponseEntity.ok(alertaService.listar());
    }

    // obtiene alerta por id
    @GetMapping("/{id}")
    public ResponseEntity<AlertaResponseDTO> buscarPorId(@PathVariable Long id) {

        // retorna alerta
        return ResponseEntity.ok(alertaService.buscarPorId(id));
    }

    // crea alerta
    @PostMapping
    public ResponseEntity<AlertaResponseDTO> crear(@Valid @RequestBody AlertaRequestDTO dto) {

        // retorna alerta creada
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(alertaService.crear(dto));
    }

    // desactiva alerta
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<AlertaResponseDTO> desactivar(@PathVariable Long id) {

        // retorna alerta desactivada
        return ResponseEntity.ok(alertaService.desactivar(id));
    }
}
