package com.example.alertas.controller;


import com.example.alertas.dto.AlertaRequestDTO;


import com.example.alertas.dto.AlertaResponseDTO;


import com.example.alertas.service.AlertaService;


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

    
    @GetMapping
    public ResponseEntity<List<AlertaResponseDTO>> listar() {

        
        return ResponseEntity.ok(alertaService.listar());
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<AlertaResponseDTO> buscarPorId(@PathVariable Long id) {

        
        return ResponseEntity.ok(alertaService.buscarPorId(id));
    }

    
    @PostMapping
    public ResponseEntity<AlertaResponseDTO> crear(@Valid @RequestBody AlertaRequestDTO dto) {

        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(alertaService.crear(dto));
    }

    
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<AlertaResponseDTO> desactivar(@PathVariable Long id) {

        
        return ResponseEntity.ok(alertaService.desactivar(id));
    }
}
